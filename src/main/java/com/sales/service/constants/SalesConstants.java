package com.sales.service.constants;

/**
 * all constants managed in this class.
 * @author ramans
 *
 */
public class SalesConstants {

	//code
	
	public static final String INVALID_FILE_CONTENT_TYPE="INVALID_FILE_CONTENT_TYPE";
	
	public static final String INVALID_FILE_SIZE="INVALID_FILE_SIZE";
	
	public static final String EMPTY_FILE_DATA="EMPTY_FILE_DATA";
	
	public static final String FILE_UPLOAD_FAILED="FILE_UPLOAD_FAILED";
	
	public static final String FILE_NOT_FOUND="FILE_NOT_FOUND";
	
	public static final String INVALID_SOCKET_ID_CLIENT="INVALID_SOCKET_ID_CLIENT";
	
	//message
	
	public static final String INVALID_FILE_CONTENT_TYPE_MSG="Uploaded invalid file content type, supported only .xls or .xlsx file content types";
	
	public static final String INVALID_FILE_SIZE_MSG="file size can't be more than 10 mb";
	
	public static final String EMPTY_FILE_DATA_MSG="file can't be empty";
	
	public static final String FILE_UPLOAD_FAILED_MSG="Unable to upload file now. try after some time";
	
	public static final String INVALID_SOCKET_ID_CLIENT_MSG="no socket bridge exist between the client and server, may be invalid socket id provided";
	
	public static final String FILE_NOT_FOUND_MSG="Unable to provides sales data as csv file not found in the server";
	// success msg
	
	public static final String FILE_RECEIVED_MSG="File Received Successfully";
	
	public static final String FILE_MEDIA_TYPE_XLSX="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	
	public static final String FILE_MEDIA_TYPE_XLS="application/vnd.ms-excel";

	
}
