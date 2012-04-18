/**
 * &copy; 2011-2012 ZengSource.com
 * 2012-2-11
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
 * 组合 {@link MultipartAction} 和 {@link MultipleAction} 的功能。
 * 
 * @author zsn
 * @since 6.0
 */
public class SuperAction extends MultipleAction {

	// + STATIC ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	private static final long serialVersionUID = 1L;

	// + FIELDS ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //
	
	private Map<String, FileItem> fileMap;

	// + CSTORS ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	// + METHODS +++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	@Override
	protected void doInit() throws MvcException {
		boolean isMultipart = ServletFileUpload.isMultipartContent(getRequest());
		if (isMultipart) {
			DiskFileItemFactory factory = new DiskFileItemFactory();
			// factory.setSizeThreshold(10 * 1024 * 1024);
			// factory.setRepository(new File(""));
			ServletFileUpload upload = new ServletFileUpload(factory);
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
							e.printStackTrace();
						}
					} else if (item.getSize() > 0) {
						String fieldName = item.getFieldName();
						if (this.fileMap == null) {
							this.fileMap = new HashMap<String, FileItem>();
						}
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
		return name == null ? null : (this.fileMap == null? null : this.fileMap.get(name));
	}

	/** 文件名不需要加扩展名。 */
	protected File saveFile(String name, String filename) throws MvcException {
		FileItem item = getFileItem(name);
		if (item != null) {
			try {
				String extension = this.getExtension(name);
				if (!(filename.endsWith(extension))) {
					filename += extension;
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
	
	protected String getExtension(String fileField) {
		FileItem item = getFileItem(fileField);
		String uploadFilename = item.getName();
		int pos = uploadFilename.lastIndexOf(".");
		if (pos > -1) {
			return uploadFilename.substring(pos).toLowerCase();
		}
		return "";
	}

	@Override
	protected void doDestroy() throws MvcException {
		if (this.fileMap != null) {
			this.fileMap.clear();
			this.fileMap = null;
		}
	}

	// + G^SETTERS +++++++++++++++++++++++++++++++++++++++++++++++++++++ //
	
	public Map<String, FileItem> getFileMap() {
		return fileMap;
	}
	
	public void setFileMap(Map<String, FileItem> fileMap) {
		this.fileMap = fileMap;
	}

	// + MAIN^TEST +++++++++++++++++++++++++++++++++++++++++++++++++++++ //
}
