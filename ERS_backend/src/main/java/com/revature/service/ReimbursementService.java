package com.revature.service;

import com.revature.controller.ExceptionController;
import com.revature.dao.ReimbursementDao;
import com.revature.dao.UserDao;
import com.revature.dto.AddReimbursementDTO;
import com.revature.dto.ResponseReimbursementDTO;
import com.revature.dto.UpdateReimbursementDTO;
import com.revature.dto.UpdateReimbursementStatusDTO;
import com.revature.exception.ImageNotFoundException;
import com.revature.exception.InvalidImageException;
import com.revature.exception.ReimbursementResolved;
import com.revature.exception.ReimbursementFinalized;
import com.revature.model.Reimbursement;
import com.revature.utility.UploadImageUtility;
import io.javalin.http.UploadedFile;
import org.apache.tika.Tika;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.TimeZone;

public class ReimbursementService {

    Logger LOGGER = LoggerFactory.getLogger(ReimbursementService.class);

    private ReimbursementDao reimbursementDao;

    public ReimbursementService() {
        this.reimbursementDao = new ReimbursementDao();
    }

    // enter mock dao here

    public ResponseReimbursementDTO addReimbursement (AddReimbursementDTO dto) throws SQLException, InvalidImageException, IOException {
        TimeZone.setDefault(TimeZone.getTimeZone("EST"));
        Timestamp submitted = Timestamp.valueOf(LocalDateTime.now());
        dto.setReimbSubmitted(submitted);

        UploadedFile uploadedImage = dto.getReimbReceipt();

        Tika tika = new Tika();
        String mimeType = tika.detect(uploadedImage.getContent());

        if (!mimeType.equals("image/jpeg") && !mimeType.equals("image/png") && !mimeType.equals("image/gif")) {
            throw new InvalidImageException("Image must be a JPEG, PNG, or GIF");
        }

        String url = UploadImageUtility.uploadImage(uploadedImage);
        System.out.println(url);

        ResponseReimbursementDTO reimbursementAdded = this.reimbursementDao.addReimbursement(dto, url);

        return reimbursementAdded;
    }

    public InputStream getReceiptImage(String reimbId, String authorId) throws SQLException, ImageNotFoundException, IOException {
        try {
            int rId = Integer.parseInt(reimbId);
            int aId = Integer.parseInt(authorId);

            InputStream is = this.reimbursementDao.getReceiptImage(rId, aId);

            if (is == null) {
                throw new ImageNotFoundException("Reimbursement ID " + reimbId + " does not have an image");
            }

            return is;
        } catch(NumberFormatException e) {
            throw new IllegalArgumentException("Assignment and/or user id must be an int value");
        }
    }


    public Reimbursement getReimbursementById(String reimbId) throws SQLException, ReimbursementResolved {
        try {
            int rId = Integer.parseInt(reimbId);
            Reimbursement reimbursement = reimbursementDao.getReimbursementById(rId);

            if (reimbursement == null) {
                throw new ReimbursementResolved("Reimbursement:  " + rId + " does not exist.");
            }

            return reimbursement;

        } catch (IllegalArgumentException | IOException e) {
            throw new IllegalArgumentException("Invalid ID, input must be an integer.");
        }
    }

    public List<ResponseReimbursementDTO> getAllReimbursements() throws SQLException, IOException {
        LOGGER.info("User requested all reimbursements.");
        return reimbursementDao.getAllReimbursements();
    }

    public List<ResponseReimbursementDTO> getReimbursementsByUser(String userId) throws SQLException, IOException {
        try {
            int uId = Integer.parseInt(userId);
            return reimbursementDao.getReimbursementsByUser(uId);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid ID given for request. Must be an integer. Input ID given: "+ userId);
        }
    }

    public boolean updateReimbursementStatus(UpdateReimbursementStatusDTO dto, String reimbId, String statusString) throws SQLException,
            ReimbursementResolved, FileNotFoundException {

        if(!(getReimbursementById(reimbId).getReimbStatus().equals("pending"))) {
            throw new ReimbursementResolved("This reimbursement has already been processed");
        }

        int statusId = reimbursementDao.getStatusId(statusString);
        int rId = Integer.parseInt(reimbId);
        TimeZone.setDefault(TimeZone.getTimeZone("EST"));
        Timestamp submitted = Timestamp.valueOf(LocalDateTime.now());
        dto.setResolveDate(submitted);
        dto.setStatusId(statusId);
        dto.setResolver(UserDao.getUserNames(dto.getResolverId()));
        System.out.println("ServiceDTO " + dto);
        return reimbursementDao.updateReimbursementStatus(dto, rId);
    }

    public Reimbursement editPendingReimbursement(String reimbId, UpdateReimbursementDTO dto) throws SQLException,
            ReimbursementResolved, ReimbursementFinalized, IOException, InvalidImageException {

        UploadedFile uploadedImage = dto.getReceipt();

        Tika tika = new Tika();
        String mimeType = tika.detect(uploadedImage.getContent());

        if (!mimeType.equals("image/jpeg") && !mimeType.equals("image/png") && !mimeType.equals("image/gif")) {
            throw new InvalidImageException("Image must be a JPEG, PNG, or GIF");
        }
        System.out.println(getReimbursementById(reimbId).toString());
        if (!(getReimbursementById(reimbId).getReimbStatus().equals("pending"))) {
            throw new ReimbursementFinalized("This reimbursement has already been processed.");
        }
        int rId = Integer.parseInt(reimbId);

        String recieptUrl = UploadImageUtility.uploadImage(dto.getReceipt());

        return reimbursementDao.editPendingReimbursement(dto,rId,recieptUrl);

    }
    public boolean deletePendingReimbursement(String reimbId, int userRoleId) throws SQLException, ReimbursementResolved, FileNotFoundException {

        if (userRoleId != 1){
            if(!(getReimbursementById(reimbId).getReimbStatus().equals("pending"))) {
                throw new ReimbursementResolved("An employee cannot delete a reimbursement that has already been approved or denied");
            }
        }
        int rId = Integer.parseInt(reimbId);
        return reimbursementDao.deleteUnresolvedReimbursement(rId);
    }

}
