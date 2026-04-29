package br.com.inspectflow.infrastructure.persistence.bucket;

import br.com.inspectflow.domain.bucket.dto.UploadRequest;
import br.com.inspectflow.domain.bucket.repository.BucketRepository;
import br.com.inspectflow.domain.equipment.enums.AttachmentType;
import br.com.inspectflow.infrastructure.persistence.bucket.repositories.DeleteFileUseCase;
import br.com.inspectflow.infrastructure.persistence.bucket.repositories.GetFileUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.Date;

@Component
@Slf4j
@RequiredArgsConstructor
public class BucketAdapterImpl implements BucketRepository {

    private final UploadFileImpl upload;

    private final PresignedUrlImpl presignedUrl;

    private final GetFileUseCase getFile;

    private final DeleteFileUseCase delete;


    @Override
    public UploadRequest uploadDocFile(String equipmentCode, AttachmentType attType, MultipartFile file){

        var fileNameLocalNameUrl= equipmentCode.toLowerCase() +"/"+ attType.getValue().toLowerCase() +"/"+ new Date().getTime();

        upload.execute(fileNameLocalNameUrl, file);

        return new UploadRequest(equipmentCode.toLowerCase() +"-"+ attType.getValue().toLowerCase(),fileNameLocalNameUrl) ;
    }

    @Override
    public String uploadImageFile(String equipmentCode,MultipartFile file){
        var fileNameLocalNameUrl= equipmentCode.toLowerCase() +"/"+ "image" +"/"+ new Date().getTime();
        upload.execute(fileNameLocalNameUrl, file);
        return fileNameLocalNameUrl;
    }

    @Override
    public String getPresignedUrl(String objectKey) {
        return presignedUrl.execute(objectKey);
    }

    @Override
    public InputStream getFile(String fileUrl) {
      return getFile.execute(fileUrl);
    }

    @Override
    public void deleteFile(String fileUrl) {
        delete.execute(fileUrl);
    }

}
