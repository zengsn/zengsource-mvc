/**
 * 
 */
package org.zengsource.mvc.action;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.zengsource.mvc.MvcException;


/**
 * @author zeng.xiaoning
 * 
 */
public class MultipartAction extends GenericAction {

	private static final long serialVersionUID = 1L;

	// ~ 对象属性 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	private Map<String, FileItem> fileMap;

	// ~ 构造方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public MultipartAction() {
		fileMap = new HashMap<String, FileItem>();
	}

	// ~ 逻辑方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	@Override
	protected void doInit() throws MvcException {
		// Check that we have a file upload request
		boolean isMultipart = ServletFileUpload.isMultipartContent(getRequest());
		if (isMultipart) {
			// Create a factory for disk-based file items
			DiskFileItemFactory factory = new DiskFileItemFactory();

			// Set factory constraints
			// factory.setSizeThreshold(10 * 1024 * 1024);
			// factory.setRepository(new File(""));

			// Create a new file upload handler
			ServletFileUpload upload = new ServletFileUpload(factory);

			// Set overall request size constraint
			// upload.setSizeMax(10 * 1024 * 1024);

			try {
				List<?> items = upload.parseRequest(getRequest());
				for (Object obj : items) {
					FileItem item = (FileItem) obj;
					if (item.isFormField()) {
						String name = item.getFieldName();
						try {
							// String value = item.getString();
							// String valueGBK = item.getString("gbk");
							// String valueGB2312 = item.getString("gb2312");
							String valueUTF = item.getString("utf-8");
							setFieldValue(name, valueUTF); // Set normal field
						} catch (UnsupportedEncodingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} else if (item.getSize() > 0) {
						String fieldName = item.getFieldName();
						this.fileMap.put(fieldName, item); // Save file
					}
				}
			} catch (FileUploadException e) {
				throw new MvcException(e);
			}

		} else { // Normal
			super.doInit();
		}
	}

	protected FileItem getFileItem(String name) throws MvcException {
		return name == null ? null : this.fileMap.get(name);
	}

	protected File saveFile(String name, String filename) throws MvcException {
		FileItem item = getFileItem(name);
		if (item != null) {
			try {
				String uploadFilename = item.getName();
				int poc = uploadFilename.lastIndexOf(".");
				if (poc > -1) {
					filename += uploadFilename.substring(poc).toLowerCase();
				}
				File diskFile = new File(filename);
				item.write(diskFile);
				return diskFile;
			} catch (Exception e) {
				logger.error("Cannot write file: " + filename);
				throw new MvcException(e);
			}
		} else {
			logger.warn("No such item: " + name);
		}
		return null;
	}

	@Override
	protected void doDestroy() throws MvcException {
		if (this.fileMap != null) {
			this.fileMap.clear();
			this.fileMap = null;
		}
	}

	// ~ g^setX ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public Map<String, FileItem> getFileMap() {
		return fileMap;
	}

	public void setFileMap(Map<String, FileItem> fileMap) {
		this.fileMap = fileMap;
	}

}
