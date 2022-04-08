package com.revature.dao;

import com.revature.dto.AddReimbursementDTO;
import com.revature.dto.ResponseReimbursementDTO;
import com.revature.dto.UpdateReimbursementDTO;
import com.revature.dto.UpdateReimbursementStatusDTO;
import com.revature.model.Reimbursement;
import com.revature.utility.ConnectionUtility;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReimbursementDao {

    public ResponseReimbursementDTO addReimbursement(AddReimbursementDTO dto, String url) throws SQLException, FileNotFoundException {
        try (Connection con = ConnectionUtility.getConnection()) {
            con.setAutoCommit(false);

            String sql = "INSERT INTO ers_reimbursement (reimb_amount, reimb_description, reimb_receipt, reimb_author, reimb_type_id) " +
                    "VALUES (?, ?, ?, ?, ?);";


            PreparedStatement pstmt1 = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            int author = dto.getReimbAuthor();

            pstmt1.setDouble(1, dto.getReimbAmount());
            //pstmt1.setTimestamp(2, dto.getReimbSubmitted());
            pstmt1.setString(2, dto.getReimbDescription());
            pstmt1.setString(3, url);
            pstmt1.setInt(4, author);
            pstmt1.setInt(5, dto.getReimbType());
            pstmt1.executeUpdate();

            ResultSet rs = pstmt1.getGeneratedKeys();
            rs.next();
            //System.out.println(rs.next());
            int reimbId = rs.getInt(1);
            System.out.println(reimbId);
            String sql2 = "SELECT * FROM all_info WHERE reimb_id = ?;";

            PreparedStatement pstmt2 = con.prepareStatement(sql2);
            pstmt2.setInt(1, reimbId);

            ResultSet rs2 = pstmt2.executeQuery();
            //System.out.println(rs2.next());
           if (rs2.next()) {
                double amount = rs2.getDouble("reimb_amount");
                String reimbSubDate = new Date(rs2.getTimestamp("reimb_submitted").getTime()).toString();
                String reimbDesc = rs2.getString("reimb_description");
                String firstName = rs2.getString("user_first_name");
                String lastName = rs2.getString("user_last_name");
                String reimbType = rs2.getString("reimb_type");
                String reimbStatus = rs2.getString("reimb_status");
                String userEmail = rs2.getString("user_email");
                String receiptUrl = rs2.getString("reimb_receipt");

                ResponseReimbursementDTO reimbursement = new ResponseReimbursementDTO(reimbId, amount, reimbSubDate, null, reimbDesc,
                        author, 0, reimbStatus, reimbType, firstName, lastName, userEmail, receiptUrl, null, null);

                con.commit();

                return reimbursement;
            } else {
                return null;
            }
        }
    }

    public Reimbursement getReimbursementById(int reimbId) throws SQLException, IOException {
        try (Connection con = ConnectionUtility.getConnection()) {
            con.setAutoCommit(false);
            String sql = "SELECT *" +
                    "FROM all_info " +
                    "WHERE reimb_id = ?";

            PreparedStatement pstmtSelect = con.prepareStatement(sql);
            pstmtSelect.setInt(1, reimbId);

            ResultSet rs = pstmtSelect.executeQuery();
            if (!rs.next())  {
                return null;
            }


            double amount = rs.getDouble("reimb_amount");

            String submitDateString = new Date(rs.getTimestamp("reimb_submitted").getTime()).toString();

            Timestamp resolvedDate = rs.getTimestamp("reimb_resolved");

            String resolvedDateString;

            if (resolvedDate != null) {
                resolvedDateString = new Date(rs.getTimestamp("reimb_submitted").getTime()).toString();
            } else {
                resolvedDateString = null;
            }

            String description = rs.getString("reimb_description");
            String receipt = rs.getString("reimb_receipt");
            String firstName = rs.getString("user_first_name");
            String lastName = rs.getString("user_last_name");
            String email = rs.getString("user_email");
            int resolverId = rs.getInt("reimb_resolver");
            String type = rs.getString("reimb_type");
            String status = rs.getString("reimb_status");
            int authorId = rs.getInt("reimb_author");


            Reimbursement reimbursement = new Reimbursement(reimbId, amount, status, type, description, submitDateString, resolvedDateString,
                    resolverId, authorId, firstName, lastName, email, receipt);
            con.commit();
            return reimbursement;
        }
    }

    public InputStream getReceiptImage(int rId, int aId) throws SQLException, IOException {
        try (Connection con = ConnectionUtility.getConnection()) {
            String sql = "SELECT reimb_receipt " +
                    "FROM all_info" +
                    "WHERE a.id = ? AND a.student_id = ?";

            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, rId);
            pstmt.setInt(2, aId);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                InputStream is = rs.getBinaryStream("reimb_receipt");

                return is;
            } else {
                return null;
            }
        }
    }

    public List<ResponseReimbursementDTO> getAllReimbursements() throws SQLException, IOException {
        try (Connection con = ConnectionUtility.getConnection()){


            String sqlSelect = "SELECT * FROM all_info;";
            PreparedStatement pstmt = con.prepareStatement(sqlSelect);

            ResultSet rs = pstmt.executeQuery();

            List<ResponseReimbursementDTO> reimbursements = new ArrayList<>();

            while (rs.next()) {
                int reimbId = rs.getInt("reimb_id");
                int author = rs.getInt("reimb_author");
                int resolver = rs.getInt("reimb_resolver");
                double amount = rs.getDouble("reimb_amount");

                String reimbDesc = rs.getString("reimb_description");
                String firstName = rs.getString("user_first_name");
                String lastName = rs.getString("user_last_name");
                String reimbType = rs.getString("reimb_type");
                String reimbStatus = rs.getString("reimb_status");
                String userEmail = rs.getString("user_email");
                String receiptUrl = rs.getString("reimb_receipt");

                String reimbSubDate = new Date(rs.getTimestamp("reimb_submitted").getTime()).toString();
                String resolverFirst = rs.getString("resolver_first");
                String resolverLast = rs.getString("resolver_last");
                String reimbResolveDate;

                if (rs.getTimestamp("reimb_resolved") != null) {
                    reimbResolveDate = new Date(rs.getTimestamp("reimb_resolved").getTime()).toString();
                }else {
                    reimbResolveDate = null;
                }


                ResponseReimbursementDTO reimbursement = new ResponseReimbursementDTO(reimbId, amount, reimbSubDate, reimbResolveDate, reimbDesc,
                        author, resolver, reimbStatus, reimbType, firstName, lastName, userEmail, receiptUrl, resolverFirst, resolverLast);
                reimbursements.add(reimbursement);
            }
            return reimbursements;
        }
    }
    public List<ResponseReimbursementDTO> getReimbursementsByUser(int userId) throws SQLException, IOException {
        try (Connection con = ConnectionUtility.getConnection()){

            String sql = "SELECT * " +
                    "FROM all_info " +
                    "WHERE reimb_author = ?;";

            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();

            List<ResponseReimbursementDTO> reimbursements = new ArrayList<>();

            while (rs.next()) {
                int reimbId = rs.getInt("reimb_id");
                System.out.println("IDID" + reimbId);
                int author = rs.getInt("reimb_author");
                int resolver = rs.getInt("reimb_resolver");
                double amount = rs.getDouble("reimb_amount");

                String reimbDesc = rs.getString("reimb_description");
                String firstName = rs.getString("user_first_name");
                String lastName = rs.getString("user_last_name");
                String reimbType = rs.getString("reimb_type");
                String reimbStatus = rs.getString("reimb_status");
                String userEmail = rs.getString("user_email");
                String receiptUrl = rs.getString("reimb_receipt");
                String resolverFirst = rs.getString("resolver_first");
                String resolverLast = rs.getString("resolver_last");

                String reimbSubDate = new Date(rs.getTimestamp("reimb_submitted").getTime()).toString();

                String reimbResolveDate;

                if (rs.getTimestamp("reimb_resolved") != null) {
                    reimbResolveDate = new Date(rs.getTimestamp("reimb_resolved").getTime()).toString();
                }else {
                    reimbResolveDate = null;
                }


                ResponseReimbursementDTO reimbursement = new ResponseReimbursementDTO(reimbId, amount, reimbSubDate, reimbResolveDate, reimbDesc,
                        author, resolver, reimbStatus, reimbType, firstName, lastName, userEmail, receiptUrl, resolverFirst, resolverLast);
                reimbursements.add(reimbursement);
            }
            return reimbursements;
        }
    }

    public Reimbursement editPendingReimbursement(UpdateReimbursementDTO reimbursement, int reimbId, String receiptUrl) throws SQLException,
            IOException {
        try (Connection con = ConnectionUtility.getConnection()) {
            con.setAutoCommit(false);

//            String typeSql = "SELECT reimb_type_id FROM ers_reimbursement_type WHERE reimb_type = ?;";
//
//            PreparedStatement psType = con.prepareStatement(typeSql);
//            System.out.println("DAO type: " + reimbursement.getType());
//            psType.setString(1, reimbursement.getType());
//            ResultSet rs1 = psType.executeQuery();
//
//            rs1.next();
//
//            int typeId = rs1.getInt("reimb_type_id");


            String sql = "UPDATE ers_reimbursement " +
                    "SET reimb_amount=?, reimb_description=?, reimb_receipt=?, reimb_type_id=? " +
                    "WHERE reimb_id = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setDouble(1, reimbursement.getAmount());
            pstmt.setString(2, reimbursement.getDesc());
            pstmt.setString(3, receiptUrl);
            pstmt.setInt(4, reimbursement.getTypeId());
            pstmt.setInt(5, reimbId);

            pstmt.executeUpdate();

            ReimbursementDao dao = new ReimbursementDao();

            con.commit();
            return dao.getReimbursementById(reimbId);
        }
    }
    public boolean updateReimbursementStatus(UpdateReimbursementStatusDTO dto, int reimbId) throws SQLException, FileNotFoundException {
        try (Connection con = ConnectionUtility.getConnection()) {
            con.setAutoCommit(false);

            String sql = "UPDATE ers_reimbursement  " +
                    "SET reimb_status_id = ?, reimb_resolved = ?, reimb_resolver = ?  " +
                    "WHERE reimb_id = ?";

            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, dto.getStatusId());
            pstmt.setTimestamp(2, dto.getResolveDate());
            pstmt.setInt(3, dto.getResolverId());
            pstmt.setInt(4, reimbId);

            if(pstmt.executeUpdate() == 1) {
                con.commit();
                return true;
            } else {
                return false;
            }
        }
    }
    public boolean deleteUnresolvedReimbursement(int reimbId) throws SQLException, FileNotFoundException {
        try (Connection con = ConnectionUtility.getConnection()) {
            con.setAutoCommit(false);
            String sql = "DELETE FROM ers_reimbursement WHERE reimb_id = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, reimbId);

            if(pstmt.executeUpdate() == 1) {
                con.commit();
                return true;
            } else {
                return false;
            }
        }
    }

    public String getStatusString(int statusId) throws SQLException, FileNotFoundException {
        try (Connection con = ConnectionUtility.getConnection()) {
            con.setAutoCommit(false);
            String sql = "SELECT reimb_status FROM ers_reimbursement_status WHERE reimb_status_id = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, statusId);
            ResultSet rs = pstmt.executeQuery(sql);

            String statusString = null;
            if(rs.next()){
                statusString = rs.getString("reimb_status");
            }

            return statusString;
        }
    }

    public int getStatusId(String statusString) throws SQLException, FileNotFoundException {
        try (Connection con = ConnectionUtility.getConnection()) {
            String sql = "SELECT reimb_status_id FROM ers_reimbursement_status WHERE reimb_status = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, statusString);

            ResultSet rs = pstmt.executeQuery();

            int statusId = 0;
            System.out.println("results(before): " + rs);
            if (rs.next()) {
                System.out.println("results(next): " + rs);
                statusId = rs.getInt("reimb_status_id");
            }

            return statusId;
        }
    }



}
