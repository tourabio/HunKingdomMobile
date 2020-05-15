/*
 * Copyright (c) 2016, Codename One
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated 
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation 
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, 
 * and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions 
 * of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, 
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A 
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT 
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF 
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE 
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE. 
 */
package Gui;

import Entities.PiecesDefectueuses;
import Entities.Repairer;
import Entities.Reparation;
import Services.PieceService;
import Utils.Statics;
import com.codename1.components.ImageViewer;
import com.codename1.components.ScaleImageLabel;
import com.codename1.components.SpanLabel;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.FileSystemStorage;
import com.codename1.ui.Button;
import com.codename1.ui.CheckBox;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Component;
import static com.codename1.ui.Component.BOTTOM;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Font;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.list.MultiList;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * The user profile form
 *
 * @author Shai Almog
 */
public class Ready extends BaseForm {
    Resources res;
    public Ready(Resources res1,String id) {
        super("ReadyPiece", BoxLayout.y());
        res = res1;
        
        
        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        getContentPane().setScrollVisible(false);
        super.addSideMenu(res);
        Image img = res.getImage("profile-background.jpg");
        if (img.getHeight() > Display.getInstance().getDisplayHeight() / 3) {
            img = img.scaledHeight(Display.getInstance().getDisplayHeight() / 3);
        }

        ScaleImageLabel sl = new ScaleImageLabel(img);
        sl.setUIID("BottomPad");
        sl.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);

        add(LayeredLayout.encloseIn(
                sl,
                BorderLayout.south(
                        GridLayout.encloseIn(3,
                                FlowLayout.encloseCenter(
                                        new Label("Ready Piece"))
                        )
                )
        ));

        Reparation r = PieceService.getInstance().listyourReparation(id);
         
         
       
            add(addItem(r));
        

    }

    public Container addItem(Reparation c) {
        ImageViewer img = null;
        Container c1 = new Container(new BoxLayout(BoxLayout.X_AXIS));
        c1.getStyle().setFgColor(0xbbc2c6);
        c1.setSize(new Dimension(20, 20));
        /*  try {
            img = new ImageViewer(Image.createImage(c.getImage()));
        } catch (IOException ex) {
            System.out.println("error : "+ex.getMessage());
        }*/
        Container c2 = new Container(new BoxLayout(BoxLayout.Y_AXIS));

        Label lprix = new Label("Price : " + Double.toString(c.getPrixRep()));
        Label des = new Label("Description: " + c.getDescription());
        Label lidRep = new Label(Integer.toString(c.getId()));
        Label lidPiece = new Label(Integer.toString(c.getPiecesdefectueuses_id()));
        lidRep.setHidden(true);
        lidPiece.setHidden(true);
        Container c3 = new Container(new BoxLayout(BoxLayout.X_AXIS));
        Button FisnishBtn = new Button("Finish");
        FisnishBtn.addActionListener(e->{
          System.out.println("Reparation : "+c);
              PieceService.getInstance().DeleteReparation(lidRep.getText(),lidPiece.getText());
              PieceService.getInstance().sendMail("khalil.tourabi10@gmail.com");
            new ListYourPieces(res).show();
           //new Payment(res,c).show();
        });
        Button RepairerBtn = new Button("repairer details");
         RepairerBtn.addActionListener(e->{
                    System.out.println("Reparation : "+c);
                  Repairer r =  PieceService.getInstance().getRepairer(c.getUserId());
                Dialog.show("Contact","First name : "+r.getFirstName()+"\n Last name  : "+r.getLastName()+"\nEmail : "+r.getEmail()+"\nPhone number : "+r.getPhone(), "OK", "Cancel");

        });
      

        c2.add(lprix);
        c2.add(des);
        c2.add(lidRep);
        c2.add(lidPiece);
        c3.add(FisnishBtn);
        c3.add(RepairerBtn);
        
        //c1.add(img);
        c1.add(c2);
        c2.add(c3);
        refreshTheme();
        return c1;
    }

}
