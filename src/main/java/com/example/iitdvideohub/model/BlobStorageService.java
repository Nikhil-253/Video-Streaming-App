package com.example.iitdvideohub.model;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobContainerClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
public class BlobStorageService {

    @Value("${azure.storage.account-name}")
    private String accountName;

    @Value("${azure.storage.account-key}")
    private String accountKey;

    public InputStream getVideoStream(String containerName, String blobName) {
        String connectionString = String.format("DefaultEndpointsProtocol=https;AccountName=%s;AccountKey=%s;EndpointSuffix=core.windows.net", accountName, accountKey);
        BlobContainerClient containerClient = new BlobContainerClientBuilder()
                .connectionString(connectionString)
                .containerName(containerName)
                .buildClient();
        BlobClient blobClient = containerClient.getBlobClient(blobName);
        return blobClient.openInputStream();
    }

    public BlobContainerClient getBlobContainerClient(String containerName) {
        String connectionString = String.format("DefaultEndpointsProtocol=https;AccountName=%s;AccountKey=%s;EndpointSuffix=core.windows.net", accountName, accountKey);
        return new BlobContainerClientBuilder()
                .connectionString(connectionString)
                .containerName(containerName)
                .buildClient();
    }
}
