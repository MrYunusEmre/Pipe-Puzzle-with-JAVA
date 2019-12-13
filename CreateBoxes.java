package OGI;

import javafx.scene.image.ImageView;

/**
 *
 * @author Muhammed Enes AKTÜRK - 150117036 , Yunus Emre ERTUNÇ - 150117064
 */
class CreateBoxes extends OGI{
        
        int index ;
        String kutuTipi ;
        String yönü ;
        boolean canMove ;
        ImageView iw ;
        
        public CreateBoxes() {
        }
        public CreateBoxes(int index, String kutuTipi, String yönü ) {
            this.index = index;
            setKutuTipi(kutuTipi);
            this.yönü = yönü;
            kutuOluştur(kutuTipi,yönü);
        }
        public void setIndex(int index) {
            this.index = index;
        }
        public int getIndex() {
            return index;
        }
        public void setKutuTipi(String kutuTipi) {
            if(kutuTipi.equals("End") || kutuTipi.equals("Starter") || kutuTipi.equals("PipeStatic")){
                canMove = false ;
            }
            this.kutuTipi = kutuTipi;
        }
        public String getKutuTipi() {
            return kutuTipi;
        }
        public void setYönü(String yönü) {
            this.yönü = yönü;
        }
        public String getYönü() {
            return yönü;
        }
        public void setCanMove(boolean canMove) {
            this.canMove = canMove;
        }
        public void setIw(ImageView iw) {
            this.iw = iw;
        }
        public ImageView getIw() {
            return iw;
        }    
        /**
        *    In kutuOlustur method, the file including the images of the tiles is read by scanner.
        *    Then, imageview named as "ImaView" is created by using parameters which are "kutuTipi" and "yönü" .
        *    After that, size of the image is adjusted.  
        */
        void kutuOluştur(String kutuTipi,String yönü){                
            ImageView iws = new ImageView(file.toURI().toString()+"\\kutular\\"+kutuTipi+yönü+".jpg");
            // read file from url and set it fit width and height , after set it into variable iw 
            iws.setFitWidth(WIDTH/9);
            iws.setFitHeight(HEIGHT/8);
            setIw(iws);        
        }

    }