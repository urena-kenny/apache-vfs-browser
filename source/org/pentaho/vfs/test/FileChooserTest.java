package org.pentaho.vfs.test;

import java.io.File;
import java.io.IOException;
import org.apache.commons.vfs.FileObject;
import org.apache.commons.vfs.FileSystemException;
import org.apache.commons.vfs.FileSystemManager;
import org.apache.commons.vfs.VFS;
import org.apache.commons.vfs.impl.DefaultFileSystemManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.pentaho.vfs.ui.VfsFileChooserDialog;

public class FileChooserTest {
  public static void main(String args[]) {
    FileSystemManager fsManager = null;
    FileObject maybeRootFile = null;
    try {
      fsManager = VFS.getManager();
      if (fsManager instanceof DefaultFileSystemManager) {
        File f = new File(".");
        try {
          ((DefaultFileSystemManager) fsManager).setBaseFile(f.getCanonicalFile());
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
      maybeRootFile = fsManager.resolveFile("jar:lib/mail.jar");
      // rootFile = fsManager.resolveFile("file:/home/mdamour/workspace/apache-vfs-browser");
      // maybeRootFile = fsManager.resolveFile("file:///c:/");
      // maybeRootFile = fsManager.resolveFile("jar:lib/mail.jar");
      // maybeRootFile = fsManager.resolveFile("ftp://ftpgolden.pentaho.org/");

      // maybeRootFile.getFileSystem().getParentLayer().

      // maybeRootFile.getFileSystem().getFileSystemManager().gets

    } catch (Exception e) {
      e.printStackTrace();
    }
    final FileObject rootFile = maybeRootFile;
    final Shell applicationShell = new Shell(SWT.SHELL_TRIM | SWT.CLOSE | SWT.MIN | SWT.MAX);
    applicationShell.setLayout(new FillLayout());
    applicationShell.setText("Application");
    applicationShell.setSize(640, 400);
    Menu bar = new Menu(applicationShell, SWT.BAR);
    applicationShell.setMenuBar(bar);
    MenuItem fileItem = new MenuItem(bar, SWT.CASCADE);
    fileItem.setText("File");
    fileItem.setAccelerator(SWT.CTRL + 'F');
    Menu fileSubMenu = new Menu(applicationShell, SWT.DROP_DOWN);
    fileItem.setMenu(fileSubMenu);
    MenuItem fileOpenItem = new MenuItem(fileSubMenu, SWT.CASCADE);
    fileOpenItem.setText("Open..");
    fileOpenItem.setAccelerator(SWT.CTRL + 'O');
    final String filters[] = new String[] { "*.*", "*.xml;*.XML" };
    final String filterNames[] = new String[] { "All Files", "XML Files" };
    fileOpenItem.addSelectionListener(new SelectionListener() {
      public void widgetDefaultSelected(SelectionEvent arg0) {
      }

      public void widgetSelected(SelectionEvent arg0) {
        FileObject initialFile = null;
//        try {
////          initialFile = rootFile.resolveFile("/home/mdamour");
//        } catch (FileSystemException e) {
//          e.printStackTrace();
//        }
        VfsFileChooserDialog fileOpenDialog = new VfsFileChooserDialog(rootFile, initialFile);
        FileObject selectedFile = fileOpenDialog.open(applicationShell, null, filters, filterNames, VfsFileChooserDialog.VFS_DIALOG_OPEN);
        if (selectedFile != null) {
          System.out.println("selectedFile = " + selectedFile.getName());
        } else {
          System.out.println("no file selected");
        }
      }
    });
    MenuItem saveAsOpenItem = new MenuItem(fileSubMenu, SWT.CASCADE);
    saveAsOpenItem.setText("Save As..");
    saveAsOpenItem.setAccelerator(SWT.CTRL + 'A');
    saveAsOpenItem.addSelectionListener(new SelectionListener() {
      public void widgetDefaultSelected(SelectionEvent arg0) {
      }

      public void widgetSelected(SelectionEvent arg0) {
        FileObject initialFile = null;
        try {
          initialFile = rootFile.resolveFile("/home/mdamour");
        } catch (FileSystemException e) {
          e.printStackTrace();
        }
        VfsFileChooserDialog fileOpenDialog = new VfsFileChooserDialog(rootFile, initialFile);
        FileObject selectedFile = fileOpenDialog.open(applicationShell, "Untitled", filters, filterNames, VfsFileChooserDialog.VFS_DIALOG_SAVEAS);
        if (selectedFile != null) {
          System.out.println("selectedFile = " + selectedFile.getName());
        } else {
          System.out.println("no file selected");
        }
      }
    });
    applicationShell.open();
    while (!applicationShell.isDisposed()) {
      if (!applicationShell.getDisplay().readAndDispatch())
        applicationShell.getDisplay().sleep();
    }
  }
}
