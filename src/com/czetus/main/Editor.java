package com.czetus.main;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JToolBar;

import com.inet.editor.BaseEditor;

public class Editor extends JFrame{

    private BaseEditor editor;
    private File lastOpen = null;
    private String openFirst;
    
    
   public Editor(){
	   editor = new BaseEditor( true );
       setLayout( new BorderLayout() );
       setVisible(true);
       setTitle("Edytor");
       setPreferredSize(new Dimension(600, 600));
       centreWindow(this);
       add( editor, BorderLayout.CENTER );
       // Gets the default toolbar of the editor. This toolbar can be null in case
       // the editor is used as a display-only component
       JToolBar toolbar = editor.getToolbar();
       if( toolbar != null ){
           // add our own actions to load and save
           toolbar.add( new LoadAction() );
           toolbar.add( new SaveAction() );
           // NOTE: custom actions will always be added to the 'general' group of the BaseEditor
           // toolbar. This group is the first one on the toolbar an will be displayed to the left.
       }
       pack();
   }
   
	public static void centreWindow(Window frame) {
	    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	    int x = (int) ((dimension.getWidth()) / 3);
	    int y = (int) ((dimension.getHeight()) / 3);
	    frame.setLocation(x, y);
	}
    
    public BaseEditor getEditor() {
		return editor;
	}

	public void setEditor(BaseEditor editor) {
		this.editor = editor;
	}



	private void openFile( String path ){
        if( path != null ){
            File file = new File( path );
            if( file.exists() && file.canRead() ){
                try {
                    editor.setPage( file.toURI().toURL() );
                    lastOpen = file;
                } catch( MalformedURLException e1 ) {
                    e1.printStackTrace();
                }
            }
        }
    }
	
    private Frame getParentFrame( Component c ){
        Container parent = c.getParent();
        while( parent != null && !(parent instanceof Frame) ){
            parent = parent.getParent(); 
        }
        return (Frame)parent;
    }
    
    private class LoadAction extends AbstractAction{
        
        /**
         * Creates the action and set's it's name to 'Load'
         */
        public LoadAction( ) {
            this.putValue( Action.NAME, "Load" );
        }

        /**
         * Opens the dialog and loads the file, if the dialog was not canceled
         * @param e the event, which activated the action, will be ignored here
         */
        @Override
        public void actionPerformed( ActionEvent e ) {
            Frame parent = getParentFrame( Editor.this );
            FileDialog openDialog = new FileDialog( parent );
            openDialog.setMode( FileDialog.LOAD );
            openDialog.setVisible( true );
            String path = openDialog.getDirectory();
            openFile( (path != null ? path + File.separator : "") + openDialog.getFile() );
        }
        
    }
    
    private class SaveAction extends AbstractAction{
        
        /**
         * Creates the action and set's it's name to 'Save'
         */
        public SaveAction( ) {
            this.putValue( Action.NAME, "Save" );
        }

        /**
         * Opens the dialog and saves the editor content to file, if the dialog was not canceled
         * @param e the event, which activated the action, will be ignored here
         */
        @Override
        public void actionPerformed( ActionEvent e ) {
            Frame parent = getParentFrame( Editor.this );
            FileDialog saveDialog = new FileDialog( parent );
            if( lastOpen != null ){
                // set the default location to the last opened file
                saveDialog.setFile( lastOpen.getName() );
                saveDialog.setDirectory( lastOpen.getParent() );
            }
            saveDialog.setMode( FileDialog.SAVE );
            saveDialog.setVisible( true );
            String fileName = saveDialog.getFile();
            String path = saveDialog.getDirectory();
            if( fileName != null ){
                File file = new File( (path != null ? path + File.separator : "") + fileName );
                FileWriter out = null;
                try {
                    out = new FileWriter( file );
                    String text = editor.getText();
                    out.write( text );
                    out.close();
                } catch( IOException e1 ) {
                    e1.printStackTrace();
                }
            }
        }
        
    }
    
}
