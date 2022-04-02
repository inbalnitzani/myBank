package abs.exception;

public class XmlException extends FileException{
   private String fileName;
    public XmlException(String fileName){
        this.fileName = fileName;
    }
    /*public String getFileName(){
        return fileName;
    }
    */
    @Override
    public String toString(){
        return "The file : "+'"' + fileName + '"'+" is not an XML file";
    }
}
