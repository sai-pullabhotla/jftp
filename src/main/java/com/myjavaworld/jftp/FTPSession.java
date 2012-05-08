/**
 *
 * This software is the confidential and proprietary information of the author,
 * Sai Pullabhotla. You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement you
 * entered into with the author.
 *
 * THE AUTHOR MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF
 * THE SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE, OR
 * NON-INFRINGEMENT. THE AUTHOR SHALL NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY
 * LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS
 * DERIVATIVES.
 *
 */
package com.myjavaworld.jftp;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.text.ParseException;
import java.util.Hashtable;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;
import javax.swing.JSplitPane;
import javax.swing.Timer;
import javax.swing.filechooser.FileSystemView;

import com.myjavaworld.ftp.ConnectionException;
import com.myjavaworld.ftp.ControlConnectionEvent;
import com.myjavaworld.ftp.ControlConnectionListener;
import com.myjavaworld.ftp.DataConnectionEvent;
import com.myjavaworld.ftp.DataConnectionListener;
import com.myjavaworld.ftp.FTPClient;
import com.myjavaworld.ftp.FTPConnectionEvent;
import com.myjavaworld.ftp.FTPConnectionListener;
import com.myjavaworld.ftp.FTPConstants;
import com.myjavaworld.ftp.FTPException;
import com.myjavaworld.ftp.FTPUtil;
import com.myjavaworld.ftp.ListParser;
import com.myjavaworld.ftp.RemoteFile;
import com.myjavaworld.gui.GUIUtil;
import com.myjavaworld.gui.MScrollPane;
import com.myjavaworld.gui.SwingWorker;
import com.myjavaworld.jftp.ssl.JFTPSSLContext;
import com.myjavaworld.util.FileChangeEvent;
import com.myjavaworld.util.FileChangeListener;
import com.myjavaworld.util.FileChangeMonitor;
import com.myjavaworld.util.Filter;
import com.myjavaworld.util.ProgressEvent;
import com.myjavaworld.util.ProgressListener;
import com.myjavaworld.util.ResourceLoader;
import com.myjavaworld.zip.ZipEvent;
import com.myjavaworld.zip.ZipListener;

/**
 * FTP session. Contains the main interface and connection and data transfer
 * logic.
 * 
 * @author Sai Pullabhotla, psai [at] jMethods [dot] com
 * @version 2.0
 */
public class FTPSession extends SessionPanel implements FTPConnectionListener,
		ControlConnectionListener, DataConnectionListener, ActionListener,
		FileChangeListener, ProgressListener, ZipListener {

	private static final ResourceBundle resources = ResourceLoader
			.getBundle("com.myjavaworld.jftp.FTPSession");
	private LocalPane localPane = null;
	private JFTP jftp = null;
	private RemotePane remotePane = null;
	private FTPClient client = null;
	private ListParser listParser = null;
	private LocalFile lwd = null;
	private RemoteFile rwd = null;
	private StatusWindow statusWindow = null;
	private StatusBar statusBar = null;
	private Timer timer = null;
	private int timeElapsed = 0;
	private long bytesTransferred = 0L;
	private MessageFormat connectStatusFormat = null;
	private MessageFormat downloadStatusFormat = null;
	private MessageFormat uploadStatusFormat = null;
	private MessageFormat listStatusFormat = null;
	private String listParentStatus = null;
	private JSplitPane splitPane1 = null;
	private JSplitPane splitPane2 = null;
	private static int sessionId = 0;
	private int id = 0;
	private RemoteHost remoteHost = null;
	private int transferType = FTPConstants.TYPE_BINARY;
	private boolean autoDetect = true;
	private boolean abort = false;
	private Filter localFileFilter = null;
	private Filter remoteFileFilter = null;
	private FileChangeMonitor fileChangeMonitor = null;
	private Hashtable monitoredTransfers = null;
	private MessageFormat zipStatusFormat = null;
	private MessageFormat unzipStatusFormat = null;
	private Timer progressTimer = null;
	private int progress = 0;

	public FTPSession(JFTP jftp) {
		super();
		this.jftp = jftp;
		this.id = ++sessionId;
		setName("cmd.session" + id);
		updateTitle();
		localFileFilter = new LocalFileFilter(null, null, false);
		connectStatusFormat = new MessageFormat(
				resources.getString("status.connect"));
		downloadStatusFormat = new MessageFormat(
				resources.getString("status.download"));
		uploadStatusFormat = new MessageFormat(
				resources.getString("status.upload"));
		listStatusFormat = new MessageFormat(resources.getString("status.list"));
		listParentStatus = resources.getString("status.listParent");
		timer = new Timer(1000, this);
		getContentPane().setLayout(new BorderLayout(0, 0));
		getContentPane().add(getCenterPanel(), BorderLayout.CENTER);
		getContentPane().add(getSouthPanel(), BorderLayout.SOUTH);
		File lwd = new File(JFTP.prefs.getLocalDirectory());
		// if (!lwd.exists() || !lwd.isDirectory())
		// lwd = JFTP.USER_HOME;
		if (!lwd.exists() || !lwd.isDirectory()) {
			lwd = FileSystemView.getFileSystemView().getDefaultDirectory();
		}
		setLocalWorkingDirectory(new LocalFile(lwd));
		addComponentListener(new ComponentAdapter() {

			@Override
			public void componentShown(ComponentEvent arg0) {
				splitPane1.setDividerLocation(0.5);
				splitPane2.setDividerLocation(0.2);
			}
		});
		fileChangeMonitor = new FileChangeMonitor();
		fileChangeMonitor.addFileChangeListener(this);
		monitoredTransfers = new Hashtable();
		zipStatusFormat = new MessageFormat(resources.getString("status.zip"));
		unzipStatusFormat = new MessageFormat(
				resources.getString("status.unzip"));
		progressTimer = new Timer(1000, this);
	}

	public void fileChanged(FileChangeEvent evt) {
		File file = evt.getFile();
		TransferObject transferObject = (TransferObject) monitoredTransfers
				.get(file);
		if (transferObject == null) {
			return;
		}
		final LocalFile localFile = transferObject.getLocalFile();
		final RemoteFile remoteFile = transferObject.getRemoteFile();
		MessageFormat formatter = new MessageFormat(
				resources.getString("confirm.uploadChangedFile"));
		String message = formatter.format(new Object[] { remoteFile,
				getRemoteHost() });
		int confirm = GUIUtil.showConfirmation(this, message);
		if (confirm != JOptionPane.YES_OPTION) {
			return;
		}
		setBusy(true);
		new SwingWorker() {

			private RemoteFile[] children = null;

			@Override
			public Object construct() {
				upload(localFile, remoteFile);
				try {
					children = client.list(remoteFileFilter);
				} catch (FTPException exp) {
					ftpException(exp);
				} catch (ConnectionException exp) {
					connectionException(exp);
				} catch (Exception exp) {
					statusWindow.addError("<ERROR> " + exp);
					exp.printStackTrace();
				}
				return null;
			}

			@Override
			public void finished() {
				remotePane.setData(rwd, children);
				setBusy(false);
			}
		}.start();
	}

	public int getId() {
		return id;
	}

	@Override
	public void setBusy(boolean busy) {
		super.setBusy(busy);
	}

	public void setTransferType(int transferType) {
		this.transferType = transferType;
	}

	public int getTransferType() {
		return transferType;
	}

	public void setAutoDetect(boolean autoDetect) {
		this.autoDetect = autoDetect;
	}

	public boolean isAutoDetect() {
		return autoDetect;
	}

	public int getTransferType(String ext) {
		if (autoDetect) {
			Object obj = JFTP.prefs.getTransferTypes().get(ext);
			if (obj == null) {
				return JFTP.prefs.getDefaultTransferType();
			} else {
				return ((Integer) obj).intValue();
			}
		}
		return transferType;
	}

	public void actionPerformed(ActionEvent evt) {
		Object source = evt.getSource();
		if (source == timer) {
			timeElapsed++;
			statusBar.setProgress((int) bytesTransferred);
			statusBar.setSpeed(bytesTransferred / timeElapsed);
			statusBar.setTimeElapsed(JFTPUtil.getTimeString(timeElapsed));
		} else if (source == progressTimer) {
			timeElapsed++;
			statusBar.setProgress(progress);
			statusBar.setTimeElapsed(JFTPUtil.getTimeString(timeElapsed));
		}
	}

	@Override
	public String toString() {
		return getTitle();
	}

	/**
	 * @param filter
	 */
	public void setLocalFileFilter(Filter filter) {
		this.localFileFilter = filter;
		localPane.refresh();
	}

	/**
	 * @return current filter set on local files.
	 */
	public Filter getLocalFileFilter() {
		return localFileFilter;
	}

	/**
	 * @param filter
	 */
	public void setRemoteFileFilter(Filter filter) {
		this.remoteFileFilter = filter;
		remotePane.refresh();
	}

	/**
	 * @return current filter set on local files.
	 */
	public Filter getRemoteFileFilter() {
		return remoteFileFilter;
	}

	private void updateTitle() {
		jftp.updateSessionTitle(this);
	}

	private Component getCenterPanel() {
		localPane = new LocalPane(this);
		remotePane = new RemotePane(this);
		statusWindow = new StatusWindow();
		MScrollPane scroller = new MScrollPane(statusWindow);

		splitPane1 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, localPane,
				remotePane);
		splitPane1.setOneTouchExpandable(true);
		splitPane1.setResizeWeight(0.5d);
		splitPane1.setContinuousLayout(true);

		splitPane2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, scroller,
				splitPane1);
		splitPane2.setOneTouchExpandable(true);
		splitPane2.setContinuousLayout(true);
		splitPane2.setResizeWeight(0.2d);

		return splitPane2;
	}

	private Component getSouthPanel() {
		statusBar = new StatusBar();
		return statusBar;
	}

	public void closeSession() {
		if (client != null && client.isConnected()) {
			try {
				client.disconnect();
			} catch (FTPException exp) {
				ftpException(exp);
			} catch (Exception exp) {
				exp.printStackTrace();
			}
		}
		fileChangeMonitor.stopMonitor();
		cleanup();
		System.gc();
	}

	public RemoteHost getRemoteHost() {
		return remoteHost;
	}

	public FTPClient getFTPClient() {
		return client;
	}

	public void setLocalWorkingDirectory(String dir) {
		File file = new File(dir);
		if (!file.isAbsolute()) {
			file = new File(lwd.getFile(), dir);
		}
		setLocalWorkingDirectory(new LocalFile(file));
	}

	public void setLocalWorkingDirectory(final LocalFile dir) {
		if (dir == null || !dir.isDirectory() || !dir.canRead()) {
			GUIUtil.showError(this,
					resources.getString("error.changeLocalDirectory"));
			return;
		}
		setBusy(true);
		new SwingWorker() {

			LocalFile[] data = null;

			@Override
			public Object construct() {
				data = dir.list(localFileFilter);
				lwd = dir;
				return null;
			}

			@Override
			public void finished() {
				localPane.setData(dir, data);
				setBusy(false);
			}
		}.start();
	}

	public LocalFile getLocalWorkingDirectory() {
		return lwd;
	}

	public void upLocalWorkingDirectory() {
		if (lwd == null) {
			return;
		}
		LocalFile parent = lwd.getParent();
		if (parent == null) {
			return;
		}
		setLocalWorkingDirectory(parent);
	}

	public void refreshLocalPane() {
		localPane.refresh();
	}

	public void setRemoteWorkingDirectory(String name) {
		RemoteFile dir = listParser.createRemoteFile(name, true);
		setRemoteWorkingDirectory(dir);
	}

	public RemoteFile getRemoteWorkingDirectory() {
		return rwd;
	}

	public void setRemoteWorkingDirectory(final RemoteFile dir) {
		if (dir == null || !(dir.isDirectory() || dir.isLink())) {
			return;
		}
		Object args = new Object[] { dir.getName() };
		String status = listStatusFormat.format(args);
		statusBar.setStatus(status);
		statusBar.setIndeterminate(true);
		setBusy(true);
		new SwingWorker() {

			RemoteFile[] data = null;

			@Override
			public Object construct() {
				try {
					client.setWorkingDirectory(dir);
					RemoteFile wd = client.getWorkingDirectory();
					rwd = wd;
					data = client.list(remoteFileFilter);
				} catch (FTPException exp) {
					ftpException(exp);
				} catch (ParseException exp) {
					statusWindow
							.addError("<ERROR> Could not parse the LIST command output. "
									+ "The original data returned by the server is: \n"
									+ exp.getMessage());
				} catch (ConnectionException exp) {
					connectionException(exp);
				} catch (Exception exp) {
					statusWindow.addError("<ERROR> " + exp);
					exp.printStackTrace();
				} finally {
					return null;
				}
			}

			@Override
			public void finished() {
				remotePane.setData(rwd, data);
				statusBar.reset();
				setBusy(false);
			}
		}.start();
	}

	public void upRemoteWorkingDirectory() {
		if (!isConnected()) {
			return;
		}
		statusBar.setStatus(listParentStatus);
		statusBar.setIndeterminate(true);
		setBusy(true);
		new SwingWorker() {

			RemoteFile[] data = null;

			@Override
			public Object construct() {
				try {
					client.setToParentDirectory();
					RemoteFile wd = client.getWorkingDirectory();
					rwd = wd;
					data = client.list(remoteFileFilter);
				} catch (FTPException exp) {
					ftpException(exp);
				} catch (ParseException exp) {
					statusWindow
							.addError("<ERROR> Could not parse the LIST command output. "
									+ "The original data returned by the server is: \n"
									+ exp.getMessage());
				} catch (ConnectionException exp) {
					connectionException(exp);
				} catch (Exception exp) {
					statusWindow.addError("<ERROR> " + exp);
					exp.printStackTrace();
				} finally {
					return null;
				}
			}

			@Override
			public void finished() {
				remotePane.setData(rwd, data);
				statusBar.reset();
				setBusy(false);
			}
		}.start();
	}

	public void refreshRemotePane() {
		remotePane.refresh();
	}

	public int getLocalFileSelectionCount() {
		return localPane.getSelectionCount();
	}

	public LocalFile getSelectedLocalFile() {
		return localPane.getSelectedFile();
	}

	public LocalFile[] getSelectedLocalFiles() {
		return localPane.getSelectedFiles();
	}

	public int getRemoteFileSelectionCount() {
		return remotePane.getSelectionCount();
	}

	public RemoteFile getSelectedRemoteFile() {
		return remotePane.getSelectedFile();
	}

	public RemoteFile[] getSelectedRemoteFiles() {
		return remotePane.getSelectedFiles();
	}

	public void connectionOpened(FTPConnectionEvent evt) {
		statusWindow.addStatus("<STATUS> " + evt.getMessage());
		statusBar.setSecured(client.isSecured());
		jftp.updateToolBar();
	}

	public void connectionClosed(FTPConnectionEvent evt) {
		setBusy(false);
		statusWindow.addStatus("<STATUS> " + evt.getMessage());
		jftp.updateToolBar();
		timer.stop();
		this.timeElapsed = 0;
		this.bytesTransferred = 0L;
		statusBar.reset();
		statusBar.setSecured(false);
		remotePane.clearAll();
		updateTitle();
	}

	public void commandSent(ControlConnectionEvent evt) {
		statusWindow.addCommand("<COMMAND> " + evt.getMessage());
	}

	public void replyReceived(ControlConnectionEvent evt) {
		statusWindow.addReply("<REPLY> " + evt.getMessage());
	}

	public void connectionDropped(ControlConnectionEvent evt) {
		statusWindow.addStatus("<STATUS>" + evt.getMessage());
	}

	public void dataTransferStarted(DataConnectionEvent evt) {
	}

	public void dataTransferFinished(DataConnectionEvent evt) {
		statusBar.setProgress(0);
	}

	public void dataTransferProgress(DataConnectionEvent evt) {
		bytesTransferred = evt.getBytesTransferred();
	}

	public void dataTransferAborted(DataConnectionEvent evt) {
		statusWindow.addStatus("<STATUS> Data transfer aborted by the user. ");
		timer.stop();
		this.timeElapsed = 0;
		this.bytesTransferred = 0L;
		statusBar.reset();
	}

	public void dataTransferError(DataConnectionEvent evt) {
	}

	public void ftpException(Exception exp) {
		statusWindow.addError(exp.getMessage());
	}

	public void exception(Throwable t) {
		statusWindow.addError(t.toString());
		t.printStackTrace();
	}

	public void connectionException(Exception exp) {
		setBusy(false);
		statusWindow.addError(exp.getMessage());
		jftp.updateToolBar();
		timer.stop();
		this.timeElapsed = 0;
		this.bytesTransferred = 0L;
		statusBar.reset();
		remotePane.clearAll();
		updateTitle();
		exp.printStackTrace();
	}

	public void connect(final RemoteHost remoteHost) {
		if (isConnected()) {
			MessageFormat format = new MessageFormat(
					resources.getString("confirm.reconnect"));
			int option = GUIUtil
					.showConfirmation(
							this,
							format.format(new Object[] { this.remoteHost,
									remoteHost }));
			if (option != JOptionPane.YES_OPTION) {
				return;
			}
			disconnect();
		}
		this.remoteHost = remoteHost;
		Object args = new Object[] {
				remoteHost.getHostName(),
				(remoteHost.getSSLUsage() == FTPConstants.USE_IMPLICIT_SSL ? new Integer(
						remoteHost.getImplicitSSLPort()) : new Integer(
						remoteHost.getPort())) };
		String status = connectStatusFormat.format(args);
		statusBar.setStatus(status);
		statusBar.setIndeterminate(true);
		setBusy(true);
		new SwingWorker() {

			RemoteFile[] data = null;

			@Override
			public Object construct() {
				try {
					client = (FTPClient) Class.forName(
							remoteHost.getFTPClientClassName()).newInstance();
					listParser = (ListParser) Class.forName(
							remoteHost.getListParserClassName()).newInstance();
					client.setListParser(listParser);
					client.setPassive(remoteHost.isPassive());
					client.setTimeout(JFTP.prefs.getTimeout());
					client.setBufferSize(JFTP.prefs.getBufferSize());
					client.setSSLUsage(remoteHost.getSSLUsage());
					client.setDataChannelUnencrypted(remoteHost
							.isDataChannelUnencrypted());
					if (remoteHost.getSSLUsage() != FTPConstants.USE_NO_SSL) {
						client.setSSLContext(JFTPSSLContext.getSSLContext(jftp,
								remoteHost.getHostName()));
					}
					client.addControlConnectionListener(FTPSession.this);
					client.addDataConnectionListener(FTPSession.this);
					client.addFTPConnectionListener(FTPSession.this);
					try {
						int port = 0;
						if (remoteHost.getSSLUsage() == FTPConstants.USE_IMPLICIT_SSL) {
							port = remoteHost.getImplicitSSLPort();
						} else {
							port = remoteHost.getPort();
						}
						client.connect(remoteHost.getHostName(), port);
						client.login(remoteHost.getUser(),
								remoteHost.getPassword(),
								remoteHost.getAccount());
					} catch (FTPException exp) {
						ftpException(exp);
						client.disconnect();
						client = null;
						return null;
					}
					String[] commands = remoteHost.getCommands();
					for (int i = 0; i < commands.length; i++) {
						client.executeCommand(commands[i]);
					}
					String initialRemoteDirectory = remoteHost
							.getInitialRemoteDirectory();
					if (initialRemoteDirectory != null
							&& initialRemoteDirectory.trim().length() > 0) {
						RemoteFile remoteDir = listParser
								.createRemoteFile(initialRemoteDirectory);
						client.setWorkingDirectory(remoteDir);
					}
					RemoteFile dir = client.getWorkingDirectory();
					data = client.list(remoteFileFilter);
					rwd = dir;
				} catch (FTPException exp) {
					ftpException(exp);
				} catch (ConnectionException exp) {
					connectionException(exp);
				} catch (Exception exp) {
					statusWindow.addError("<ERROR> " + exp.getMessage());
					exp.printStackTrace();
				} finally {
					return null;
				}
			}

			@Override
			public void finished() {
				if (client != null && client.isConnected()) {
					remotePane.setData(rwd, data);
					updateTitle();
				}
				statusBar.reset();
				setBusy(false);
				String initialLocalDirectory = remoteHost
						.getInitialLocalDirectory();
				setLocalWorkingDirectory(initialLocalDirectory);
			}
		}.start();
	}

	public void reconnect() {
		if (remoteHost != null) {
			connect(this.remoteHost);
		}
	}

	public void download() {
		if (!isConnected()) {
			return;
		}
		final RemoteFile[] selectedFiles = remotePane.getSelectedFiles();
		if (selectedFiles == null || selectedFiles.length == 0) {
			return;
		}
		abort = false;
		setBusy(true);
		new SwingWorker() {

			LocalFile[] children = null;

			@Override
			public Object construct() {
				for (int i = 0; i < selectedFiles.length; i++) {
					if (!client.isConnected() || abort) {
						break;
					}
					download(rwd, lwd.getFile(), selectedFiles[i]);
				}
				children = lwd.list(localFileFilter);
				return null;
			}

			@Override
			public void finished() {
				localPane.setData(lwd, children);
				setBusy(false);
			}
		}.start();
	}

	public void download(RemoteFile sourceDir, File targetDir, RemoteFile source) {
		download(sourceDir, targetDir, source, null);
	}

	public void download(RemoteFile sourceDir, File targetDir,
			RemoteFile source, String targetName) {

		if (client == null || !client.isConnected() || abort) {
			return;
		}
		if (source.isDirectory()
				&& (source.getName().equals(".") || source.getName().equals(
						".."))) {
			return;
		}
		if (targetName == null) {
			targetName = source.getNormalizedName();
		}
		if (source.isFile()) {
			File destination = new File(targetDir, targetName);
			// int type = getTransferType(source.getExtension());
			// Object[] args = {source.getName()};
			// statusBar.setStatus(downloadStatusFormat.format(args));
			// statusBar.setMaximum((int) source.getSize());
			// timeElapsed = 0;
			// timer.start();
			// try {
			// client.download(source, destination, type, false);
			// }
			// catch(FTPException exp) {
			// ftpException(exp);
			// }
			// catch(ConnectionException exp) {
			// connectionException(exp);
			// }
			// catch(Exception exp) {
			// statusWindow.addError("<ERROR> " + exp);
			// exp.printStackTrace();
			// }
			// finally {
			// timer.stop();
			// timeElapsed = 0;
			// statusBar.reset();
			// }
			downloadDataFile(source, destination);
		} else if (source.isDirectory()) {
			// File target = new File(targetDir, source.getName());
			File target = new File(targetDir, targetName);
			if (!target.exists()) {
				target.mkdirs();
			}
			RemoteFile[] children = null;
			try {
				client.setWorkingDirectory(source);
				children = client.list(remoteFileFilter);
			} catch (FTPException exp) {
				ftpException(exp);
			} catch (ConnectionException exp) {
				connectionException(exp);
			} catch (Exception exp) {
				statusWindow.addError("<ERROR> " + exp);
				exp.printStackTrace();
			}
			if (children != null) {
				for (int i = 0; i < children.length; i++) {
					download(source, target, children[i], null);
				}
			}
		}
	}

	public void downloadDataFile(RemoteFile source, File target) {
		int type = getTransferType(source.getExtension());
		Object[] args = { source.getName() };
		statusBar.setStatus(downloadStatusFormat.format(args));
		statusBar.setMaximum((int) source.getSize());
		timeElapsed = 0;
		timer.start();
		try {
			client.download(source, target, type, false);
		} catch (FTPException exp) {
			ftpException(exp);
		} catch (ConnectionException exp) {
			connectionException(exp);
		} catch (Exception exp) {
			statusWindow.addError("<ERROR> " + exp);
			exp.printStackTrace();
		} finally {
			timer.stop();
			timeElapsed = 0;
			statusBar.reset();
		}
	}

	public void upload() {
		if (!isConnected()) {
			return;
		}
		final LocalFile[] selectedFiles = localPane.getSelectedFiles();
		if (selectedFiles == null || selectedFiles.length == 0) {
			return;
		}
		abort = false;
		setBusy(true);
		new SwingWorker() {

			RemoteFile[] children = null;

			@Override
			public Object construct() {
				for (int i = 0; i < selectedFiles.length; i++) {
					if (!client.isConnected() || abort) {
						break;
					}
					upload(lwd, rwd, selectedFiles[i]);
				}
				try {
					children = client.list(remoteFileFilter);
				} catch (FTPException exp) {
					ftpException(exp);
				} catch (ConnectionException exp) {
					connectionException(exp);
				} catch (Exception exp) {
					statusWindow.addError("<ERROR> " + exp);
					exp.printStackTrace();
				} finally {
					return null;
				}
			}

			@Override
			public void finished() {
				remotePane.setData(rwd, children);
				setBusy(false);
			}
		}.start();
	}

	public void upload(LocalFile sourceDir, RemoteFile targetDir,
			LocalFile source) {
		upload(sourceDir, targetDir, source, null);
	}

	public void upload(LocalFile sourceDir, RemoteFile targetDir,
			LocalFile source, String targetName) {
		if (client == null || !client.isConnected() || abort) {
			return;
		}
		if (source.isDirectory()
				&& (source.getName().equals(".") || source.getName().equals(
						".."))) {
			return;
		}
		if (targetName == null) {
			targetName = source.getName();
		}
		if (source.isFile()) {
			// RemoteFile destination = listParser.createRemoteFile(targetDir,
			// source.getName(), false);
			RemoteFile destination = listParser.createRemoteFile(targetDir,
					targetName, false);
			int type = getTransferType(source.getExtension());
			Object[] args = { destination.getName() };
			statusBar.setStatus(uploadStatusFormat.format(args));
			statusBar.setMaximum((int) source.getSize());
			timeElapsed = 0;
			timer.start();
			try {
				client.upload(source.getFile(), destination, type, false, 0L);
			} catch (FTPException exp) {
				ftpException(exp);
			} catch (ConnectionException exp) {
				connectionException(exp);
			} catch (Exception exp) {
				statusWindow.addError("<ERROR> " + exp);
				exp.printStackTrace();
			} finally {
				timer.stop();
				timeElapsed = 0;
				statusBar.reset();
			}
		} else if (source.isDirectory()) {
			// RemoteFile destination = listParser.createRemoteFile(targetDir,
			// source.getName(), true);
			RemoteFile destination = listParser.createRemoteFile(targetDir,
					targetName, true);
			try {
				client.createDirectory(destination);
			} catch (FTPException exp) {
				ftpException(exp);
			} catch (ConnectionException exp) {
				connectionException(exp);
			} catch (Exception exp) {
				statusWindow.addError("<ERROR> " + exp);
				exp.printStackTrace();
			}
			LocalFile[] children = source.list(localFileFilter);
			for (int i = 0; i < children.length; i++) {
				upload(source, destination, children[i], null);
			}
		}
	}

	public void upload(LocalFile localFile, String remoteFileName) {
		RemoteFile remoteFile = listParser
				.createRemoteFile(rwd, remoteFileName);
		upload(localFile, remoteFile);
	}

	public void upload(LocalFile localFile, RemoteFile destination) {
		int type = getTransferType(localFile.getExtension());
		Object[] args = { destination.getName() };
		statusBar.setStatus(uploadStatusFormat.format(args));
		statusBar.setMaximum((int) localFile.getSize());
		timeElapsed = 0;
		timer.start();
		try {
			client.upload(localFile.getFile(), destination, type, false, 0L);
		} catch (FTPException exp) {
			ftpException(exp);
		} catch (ConnectionException exp) {
			connectionException(exp);
		} catch (Exception exp) {
			statusWindow.addError("<ERROR> " + exp);
			exp.printStackTrace();
		} finally {
			timer.stop();
			timeElapsed = 0;
			statusBar.reset();
		}
	}

	public void createRemoteDirectory(final String directory) {
		setBusy(true);
		new SwingWorker() {

			RemoteFile[] data = null;

			@Override
			public Object construct() {
				RemoteFile dir = listParser.createRemoteFile(directory, true);
				try {
					client.createDirectory(dir);
					data = client.list(remoteFileFilter);
				} catch (FTPException exp) {
					ftpException(exp);
				} catch (ConnectionException exp) {
					connectionException(exp);
				} catch (Exception exp) {
					statusWindow.addError("<ERROR> " + exp.getMessage());
					exp.printStackTrace();
				} finally {
					return null;
				}
			}

			@Override
			public void finished() {
				if (data != null) {
					remotePane.setData(rwd, data);
				}
				setBusy(false);
			}
		}.start();
	}

	public void createRemoteFile(final String file) {
		setBusy(true);
		new SwingWorker() {

			RemoteFile[] data = null;

			@Override
			public Object construct() {
				RemoteFile newFile = listParser.createRemoteFile(file, false);
				try {
					client.createFile(newFile);
					data = client.list(remoteFileFilter);
				} catch (FTPException exp) {
					ftpException(exp);
				} catch (ConnectionException exp) {
					connectionException(exp);
				} catch (Exception exp) {
					statusWindow.addError("<ERROR> " + exp.getMessage());
					exp.printStackTrace();
				} finally {
					return null;
				}
			}

			@Override
			public void finished() {
				if (data != null) {
					remotePane.setData(rwd, data);
				}
				setBusy(false);
			}
		}.start();
	}

	public void renameRemoteFile(final String fromName, final String toName) {
		setBusy(true);
		new SwingWorker() {

			RemoteFile[] data = null;

			@Override
			public Object construct() {
				RemoteFile from = listParser.createRemoteFile(fromName);
				RemoteFile to = listParser.createRemoteFile(toName);
				try {
					client.rename(from, to);
					data = client.list(remoteFileFilter);
				} catch (FTPException exp) {
					ftpException(exp);
				} catch (Exception exp) {
					System.err.println(exp);
					exp.printStackTrace();
				} finally {
					return null;
				}
			}

			@Override
			public void finished() {
				if (data != null) {
					remotePane.setData(rwd, data);
				}
				setBusy(false);
			}
		}.start();
	}

	public void changeRemoteFilePermissions(final RemoteFile oldFile,
			final RemoteFile newFile, final boolean recursive) {
		setBusy(true);
		new SwingWorker() {

			RemoteFile[] data = null;

			@Override
			public Object construct() {
				try {
					String newAttributes = newFile.getAttributes();
					newAttributes = newAttributes.substring(1);
					if (recursive) {
						changeRemoteFilePermissions(oldFile,
								FTPUtil.parseAttributes(newAttributes));
					} else {
						String fileName = oldFile.getNormalizedPath();
						// if(fileName.indexOf(' ') > 0) {
						// fileName = "\"" + fileName + "\"";
						// }
						client.executeCommand("SITE CHMOD "
								+ FTPUtil.parseAttributes(newAttributes) + " "
								+ fileName);
					}
					client.setWorkingDirectory(rwd);
					data = client.list(remoteFileFilter);
				} catch (FTPException exp) {
					ftpException(exp);
				} catch (Exception exp) {
					System.err.println(exp);
					exp.printStackTrace();
				} finally {
					return null;
				}
			}

			@Override
			public void finished() {
				if (data != null) {
					remotePane.setData(rwd, data);
				}
				setBusy(false);
			}
		}.start();
	}

	private void changeRemoteFilePermissions(RemoteFile file, String attributes) {
		if (client == null || !client.isConnected() || abort) {
			return;
		}
		if (file.isDirectory()
				&& (file.getName().equals(".") || file.getName().equals(".."))) {
			return;
		}
		String fileName = file.getNormalizedPath();
		// if(fileName.indexOf(' ') > 0) {
		// fileName = "\"" + fileName + "\"";
		// }
		try {
			client.executeCommand("SITE CHMOD " + attributes + " " + fileName);
		} catch (FTPException exp) {
			ftpException(exp);
		} catch (ConnectionException exp) {
			connectionException(exp);
		}
		if (file.isDirectory()) {
			RemoteFile[] children = null;
			try {
				client.setWorkingDirectory(file);
				children = client.list(remoteFileFilter);
			} catch (FTPException exp) {
				ftpException(exp);
			} catch (ConnectionException exp) {
				connectionException(exp);
			} catch (Exception exp) {
				statusWindow.addError("<ERROR> " + exp);
				exp.printStackTrace();
			}
			if (children != null) {
				for (int i = 0; i < children.length; i++) {
					changeRemoteFilePermissions(children[i], attributes);
				}
			}
		}
	}

	public void updateRemoteFile(final RemoteFile oldFile,
			final RemoteFile newFile) {
		setBusy(true);
		new SwingWorker() {

			RemoteFile[] data = null;

			@Override
			public Object construct() {
				try {
					boolean reload = false;
					String oldName = oldFile.getName();
					String newName = newFile.getName();
					if (!oldName.equals(newName)) {
						client.rename(oldFile, newFile);
						reload = true;
					}
					String oldAttributes = oldFile.getAttributes();
					String newAttributes = newFile.getAttributes();
					if (!oldAttributes.equals(newAttributes)) {
						newAttributes = newAttributes.substring(1);
						client.executeCommand("SITE CHMOD " + newName + " "
								+ FTPUtil.parseAttributes(newAttributes));
						reload = true;
					}
					if (reload) {
						data = client.list(remoteFileFilter);
					}
				} catch (Exception exp) {
					System.err.println(exp);
					exp.printStackTrace();
				} finally {
					return null;
				}
			}

			@Override
			public void finished() {
				if (data != null) {
					remotePane.setData(rwd, data);
				}
				setBusy(false);
			}
		}.start();
	}

	public void deleteRemoteFiles() {
		final RemoteFile[] selectedFiles = remotePane.getSelectedFiles();
		if (selectedFiles == null || selectedFiles.length == 0) {
			return;
		}
		int option = GUIUtil.showConfirmation(jftp,
				resources.getString("confirm.deleteRemoteFiles"));
		if (option != JOptionPane.YES_OPTION) {
			return;
		}
		setBusy(true);
		new SwingWorker() {

			RemoteFile[] data = null;

			@Override
			public Object construct() {
				for (int i = 0; i < selectedFiles.length; i++) {
					delete(selectedFiles[i]);
				}
				try {
					client.setWorkingDirectory(rwd);
					data = client.list(remoteFileFilter);
				} catch (FTPException exp) {
					ftpException(exp);
				} catch (ConnectionException exp) {
					connectionException(exp);
				} catch (Exception exp) {
					statusWindow.addError("<ERROR> " + exp);
					exp.printStackTrace();
				} finally {
					return null;
				}
			}

			@Override
			public void finished() {
				remotePane.setData(rwd, data);
				setBusy(false);
			}
		}.start();
	}

	private void delete(RemoteFile file) {
		if (client == null || !client.isConnected()) {
			return;
		}
		if (file.isDirectory()
				&& (file.getName().equals(".") || file.getName().equals(".."))) {
			return;
		}
		try {
			if (file.isFile()) {
				client.delete(file);
			} else if (file.isDirectory()) {
				RemoteFile[] children = null;
				client.setWorkingDirectory(file);
				children = client.list(remoteFileFilter);
				for (int i = 0; i < children.length; i++) {
					delete(children[i]);
				}
				client.setToParentDirectory();
				client.delete(file);
			}
		} catch (FTPException exp) {
			ftpException(exp);
		} catch (ConnectionException exp) {
			connectionException(exp);
		} catch (Exception exp) {
			statusWindow.addError("<ERROR> " + exp);
			exp.printStackTrace();
		}
	}

	public void createLocalDirectory(final String dir) {
		setBusy(true);
		new SwingWorker() {

			LocalFile[] data = null;
			boolean created = true;

			@Override
			public Object construct() {
				File file = new File(dir);
				if (!file.isAbsolute()) {
					file = new File(lwd.getFile(), dir);
				}
				created = file.mkdirs();
				if (created) {
					data = lwd.list(localFileFilter);
				}
				return null;
			}

			@Override
			public void finished() {
				if (!created) {
					GUIUtil.showError(jftp,
							resources.getString("error.createLocalDirectory"));
				} else {
					localPane.setData(lwd, data);
				}
				setBusy(false);
			}
		}.start();
	}

	public void createLocalFile(final String fileName) {
		setBusy(true);
		new SwingWorker() {

			LocalFile[] data = null;
			boolean created = true;

			@Override
			public Object construct() {
				File file = new File(fileName);
				if (!file.isAbsolute()) {
					file = new File(lwd.getFile(), fileName);
				}
				try {
					created = file.createNewFile();
					if (created) {
						data = lwd.list(localFileFilter);
					}
				} catch (Exception exp) {
					GUIUtil.showError(jftp, exp);
				}
				return null;
			}

			@Override
			public void finished() {
				if (!created) {
					GUIUtil.showError(jftp,
							resources.getString("error.createLocalFile"));
				} else {
					localPane.setData(lwd, data);
				}
				setBusy(false);
			}
		}.start();
	}

	public void renameLocalFile(String fromName, String toName) {
		File fromFile = new File(fromName);
		File toFile = new File(toName);
		if (!fromFile.isAbsolute()) {
			fromFile = new File(lwd.getFile(), fromName);
		}
		if (!toFile.isAbsolute()) {
			toFile = new File(lwd.getFile(), toName);
		}
		boolean renamed = fromFile.renameTo(toFile);
		if (renamed) {
			LocalFile[] children = lwd.list(localFileFilter);
			localPane.setData(lwd, children);
		} else {
			GUIUtil.showError(this,
					resources.getString("error.renameLocalFile"));
		}
	}

	public void deleteLocalFiles() {
		final LocalFile[] selectedFiles = localPane.getSelectedFiles();
		if (selectedFiles == null || selectedFiles.length == 0) {
			return;
		}
		int option = GUIUtil.showConfirmation(this,
				resources.getString("conform.deleteLocalFiles"));
		if (option != JOptionPane.YES_OPTION) {
			return;
		}

		setBusy(true);

		new SwingWorker() {

			LocalFile[] data = null;

			@Override
			public Object construct() {
				for (int i = 0; i < selectedFiles.length; i++) {
					// delete(selectedFiles[i].getFile());
					delete(selectedFiles[i]);
				}
				data = lwd.list(localFileFilter);
				return null;
			}

			@Override
			public void finished() {
				setBusy(false);
				localPane.setData(lwd, data);
			}
		}.start();
	}

	private void delete(LocalFile file) {
		if (file.isDirectory()
				&& (file.getName().equals(".") || file.getName().equals(".."))) {
			return;
		}
		if (file.isFile()) {
			file.delete();
		} else if (file.isDirectory()) {
			LocalFile[] children = file.list(localFileFilter);
			for (int i = 0; i < children.length; i++) {
				delete(children[i]);
			}
			file.delete();
		}
	}

	public void executeCommand(final String command) {
		if (client == null || !client.isConnected()) {
			return;
		}
		setBusy(true);
		new SwingWorker() {

			@Override
			public Object construct() {
				try {
					client.executeCommand(command);
				} catch (FTPException exp) {
					ftpException(exp);
				} catch (ConnectionException exp) {
					connectionException(exp);
				} catch (Exception exp) {
					statusWindow.addError(exp.toString());
					exp.printStackTrace();
				} finally {
					return null;
				}
			}

			@Override
			public void finished() {
				setBusy(false);
			}
		}.start();
	}

	public void executeCommands(final String[] commands) {
		if (commands == null || client == null || !client.isConnected()) {
			return;
		}
		setBusy(true);
		new SwingWorker() {

			@Override
			public Object construct() {
				try {
					for (int i = 0; i < commands.length; i++) {
						String command = commands[i].toUpperCase();
						if (!(command.startsWith("LIST")
								|| command.startsWith("NLST")
								|| command.startsWith("RETR")
								|| command.startsWith("STOR")
								|| command.startsWith("STOU")
								|| command.startsWith("APPE")
								|| command.startsWith("PASV")
								|| command.startsWith("PORT")
								|| command.startsWith("DATA")
								|| command.startsWith("CWD") || command
									.startsWith("CDUP"))) {
							client.executeCommand(commands[i]);
						}
					}
				} catch (FTPException exp) {
					ftpException(exp);
				} catch (ConnectionException exp) {
					connectionException(exp);
				} catch (Exception exp) {
					statusWindow.addError(exp.toString());
					exp.printStackTrace();
				} finally {
					return null;
				}
			}

			@Override
			public void finished() {
				setBusy(false);
			}
		}.start();
	}

	public void disconnect() {
		if (client == null || !client.isConnected()) {
			return;
		}
		setBusy(true);
		new SwingWorker() {

			@Override
			public Object construct() {
				try {
					client.disconnect();
				} catch (FTPException exp) {
					ftpException(exp);
				} catch (ConnectionException exp) {
					connectionException(exp);
				} catch (Exception exp) {
					statusWindow.addError("<ERROR> " + exp);
					exp.printStackTrace();
				} finally {
					return null;
				}
			}

			@Override
			public void finished() {
				if (timer.isRunning()) {
					timer.stop();
				}
				statusBar.reset();
				bytesTransferred = 0L;
				timeElapsed = 0;
				remotePane.clearAll();
				updateTitle();
				setBusy(false);
			}
		}.start();
	}

	public boolean isConnected() {
		if (client == null) {
			return false;
		}
		return client.isConnected();
	}

	private void cleanup() {
		localPane = null;
		remotePane = null;
		client = null;
		listParser = null;
		lwd = null;
		rwd = null;
		statusWindow = null;
		statusBar = null;
		timer = null;
		downloadStatusFormat = null;
		uploadStatusFormat = null;
		listStatusFormat = null;
		splitPane1 = null;
		splitPane2 = null;
		remoteHost = null;
	}

	public File downloadToTempFile(RemoteFile source, boolean monitor) {
		if (!source.isFile()) {
			return null;
		}
		File destination = null;
		try {
			destination = File.createTempFile("jftp",
					"." + source.getExtension());
			destination.deleteOnExit();
		} catch (IOException exp) {
			statusWindow.addError("<ERROR> " + exp);
			exp.printStackTrace();
			return null;
		}
		int type = getTransferType(source.getExtension());
		Object[] args = { source.getName() };
		statusBar.setStatus(downloadStatusFormat.format(args));
		statusBar.setMaximum((int) source.getSize());
		timeElapsed = 0;
		timer.start();
		try {
			client.download(source, destination, type, false);
			if (monitor) {
				fileChangeMonitor.add(destination);
				monitoredTransfers.put(destination, new TransferObject(
						TransferObject.UPLOAD, new LocalFile(destination),
						source));
			}
			return destination;
		} catch (FTPException exp) {
			ftpException(exp);
			return null;
		} catch (ConnectionException exp) {
			connectionException(exp);
			return null;
		} catch (Exception exp) {
			statusWindow.addError("<ERROR> " + exp);
			exp.printStackTrace();
			return null;
		} finally {
			timer.stop();
			timeElapsed = 0;
			statusBar.reset();
		}
	}

	public void updateToolBar() {
		jftp.updateToolBar();
	}

	public void setAbortFlag(boolean abort) {
		this.abort = abort;
	}

	public void clearAbortFlag() {
		abort = false;
	}

	public JFTP getJFTP() {
		return jftp;
	}

	public void setStatus(String status) {
		statusBar.setStatus(status);
	}

	public void resetStatusBar() {
		statusBar.reset();
	}

	public void setProgress(int progress) {
		statusBar.setProgress(progress);
	}

	public void progressChanged(ProgressEvent evt) {
		progress = evt.getProgress();
	}

	public void beginFile(ZipEvent evt) {
		Object[] args = { evt.getFile() };
		if (evt.getType() == ZipEvent.ZIP) {
			statusBar.setStatus(zipStatusFormat.format(args));
		} else if (evt.getType() == ZipEvent.UNZIP) {
			statusBar.setStatus(unzipStatusFormat.format(args));
		}
		progressTimer.start();
	}

	public void endFile(ZipEvent evt) {
		progressTimer.stop();
		timeElapsed = 0;
		statusBar.reset();
	}

	public void selectAllLocalFiles() {
		localPane.selectAll();
	}

	public void invertLocalFileSelection() {
		localPane.invertSelection();
	}

	public void selectAllRemoteFiles() {
		remotePane.selectAll();
	}

	public void invertRemoteFileSelection() {
		remotePane.invertSelection();
	}
}