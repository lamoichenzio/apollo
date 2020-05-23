package it.univaq.disim.mwt.apollo.business.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(MultipartFile.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        MultipartFile file = (MultipartFile) target;
        if(!file.isEmpty()){
            if(!file.getContentType().equals("image/png") && !file.getContentType().equals("image/jpeg")) {
                errors.rejectValue("file", "wrongFileType");
            }
        }
    }
}
