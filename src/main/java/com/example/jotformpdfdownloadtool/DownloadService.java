package com.example.jotformpdfdownloadtool;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class DownloadService {

    private static Logger LOG = LoggerFactory.getLogger(DownloadService.class);

    @Value("${app.jotform.apikey}")
    private String apiKey;

    @Value("${app.jotform.pdf.download.location}")
    private String downloadFolder;


    public void startDownload() {

        LOG.info("Start fetching user form submissions...");

        HttpResponse<String> response = Unirest.get("https://api.jotform.com/user/submissions?limit=1000")
                .header("APIKEY", apiKey)
                .asString();

        try {
            JsonNode contentNode = new ObjectMapper().readTree(response.getBody()).get("content");
            if (contentNode.isArray()) {
                contentNode.forEach(c -> {
                    String submissionId = c.get("id").asText();
                    String formId = c.get("form_id").asText();

                    LOG.info("Downloading PDF for form submission id {} ", submissionId);
                    downloadFile(formId, submissionId);

                });
            }
        } catch (JsonProcessingException e) {
            LOG.error("Error processing api response {}", e.getMessage());
        }
    }

    public void downloadFile(String formId, String submissionId) {
        try {
            String downloadUrl = "https://submit.jotform.com/server.php?action=getSubmissionPDF&sid={submissionid}&formID={formid}"
                    .replace("{submissionid}", submissionId)
                    .replace("{formid}", formId);

            String formPath = downloadFolder + "/" + formId;
            String pdfPath = formPath + "/" + submissionId + ".pdf";
            File formDir = new File(formPath);
            formDir.mkdirs();

            Unirest.get(downloadUrl).asFile(pdfPath);

            LOG.info("Downloaded {}.", pdfPath);

        } catch (Exception e) {
            LOG.error("Error downloading pdf {}", e.getMessage());
        }
    }
}
