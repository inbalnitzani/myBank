package abs.exception;

public class XmlException extends Exception{
   private String fileName;
    public XmlException(String fileName){
        this.fileName = fileName;
    }
    public String getFileName(){
        return fileName;
    }
}
