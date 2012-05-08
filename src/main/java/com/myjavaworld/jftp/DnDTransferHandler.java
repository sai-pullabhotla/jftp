/*
 * Copyright 2012 jMethods, Inc. 
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.myjavaworld.jftp;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.TransferHandler;

import com.myjavaworld.ftp.RemoteFile;
import com.myjavaworld.jftp.actions.DownloadAction;
import com.myjavaworld.jftp.actions.UploadAction;

/**
 * @author Sai Pullabhotla
 */
public class DnDTransferHandler extends TransferHandler {

	private static final Icon DOWNLOAD_ICON = JFTPUtil
			.getIcon("download16.gif");
	private static final Icon UPLOAD_ICON = JFTPUtil.getIcon("upload16.gif");
	private static final DataFlavor LOCAL_FILE_FLAVOR = new DataFlavor(
			LocalFile[].class, "localFileList");
	private static final DataFlavor REMOTE_FILE_FLAVOR = new DataFlavor(
			RemoteFile[].class, "remoteFileList");
	private JFTP jftp = null;

	public DnDTransferHandler(JFTP jftp) {
		this.jftp = jftp;
	}

	@Override
	public int getSourceActions(JComponent c) {
		return COPY;
	}

	@Override
	public boolean canImport(JComponent comp, DataFlavor[] transferFlavors) {
		if ("localPane".equals(comp.getName())) {
			for (int i = 0; i < transferFlavors.length; i++) {
				if (transferFlavors[i].equals(REMOTE_FILE_FLAVOR)) {
					return true;
				}
			}
			return false;
		}
		if ("remotePane".equals(comp.getName())) {
			for (int i = 0; i < transferFlavors.length; i++) {
				if (transferFlavors[i].equals(LOCAL_FILE_FLAVOR)) {
					return true;
				}
			}
			return false;
		}
		return false;
	}

	@Override
	protected Transferable createTransferable(JComponent c) {
		if ("localPane".equals(c.getName())) {
			return new LocalFileTransferable(jftp.getCurrentSession()
					.getSelectedLocalFiles());
		} else if ("remotePane".equals(c.getName())) {
			return new RemoteFileTransferable(jftp.getCurrentSession()
					.getSelectedRemoteFiles());
		} else {
			return null;
		}
	}

	@Override
	public boolean importData(JComponent comp, Transferable t) {
		if ("localPane".equals(comp.getName())) {
			DownloadAction.getInstance(jftp).actionPerformed(null);
			return true;
		} else if ("remotePane".equals(comp.getName())) {
			UploadAction.getInstance(jftp).actionPerformed(null);
			return true;
		}
		return false;
	}

	@Override
	public Icon getVisualRepresentation(Transferable t) {
		try {
			String data = (String) t.getTransferData(DataFlavor.stringFlavor);
			if ("cmd.upload".equals(data)) {
				return UPLOAD_ICON;
			} else if ("cmd.download".equals(data)) {
				return DOWNLOAD_ICON;
			}
		} catch (UnsupportedFlavorException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return super.getVisualRepresentation(t);
	}

	private class LocalFileTransferable implements Transferable {

		final DataFlavor[] supportedFlavors = new DataFlavor[] { LOCAL_FILE_FLAVOR };
		private LocalFile[] data = null;

		public LocalFileTransferable(LocalFile[] data) {
			this.data = data;
		}

		public DataFlavor[] getTransferDataFlavors() {
			return supportedFlavors;
		}

		public boolean isDataFlavorSupported(DataFlavor flavor) {
			return flavor.equals(LOCAL_FILE_FLAVOR);
		}

		public Object getTransferData(DataFlavor flavor)
				throws UnsupportedFlavorException, IOException {
			if (flavor.equals(LOCAL_FILE_FLAVOR)) {
				return data;
			}
			throw new UnsupportedFlavorException(flavor);
		}
	}

	private class RemoteFileTransferable implements Transferable {

		final DataFlavor[] supportedFlavors = new DataFlavor[] { REMOTE_FILE_FLAVOR };
		private RemoteFile[] data = null;

		public RemoteFileTransferable(RemoteFile[] data) {
			this.data = data;
		}

		public DataFlavor[] getTransferDataFlavors() {
			return supportedFlavors;
		}

		public boolean isDataFlavorSupported(DataFlavor flavor) {
			return flavor.equals(REMOTE_FILE_FLAVOR);
		}

		public Object getTransferData(DataFlavor flavor)
				throws UnsupportedFlavorException, IOException {
			if (flavor.equals(REMOTE_FILE_FLAVOR)) {
				return data;
			}
			throw new UnsupportedFlavorException(flavor);
		}
	}
}