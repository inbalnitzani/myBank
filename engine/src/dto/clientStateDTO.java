package dto;

public class clientStateDTO {
    Boolean rewind;
    Integer time;
    ClientDTO client;

    public clientStateDTO(Boolean rewind,Integer time,ClientDTO client){
        this.time = time;
        this.rewind = rewind;
        this.client = client;
    }

    public void setRewind(Boolean rewind) {
        this.rewind = rewind;
    }

    public Integer getTime() {
        return time;
    }

    public Boolean getRewind() {
        return rewind;
    }

    public ClientDTO getClient() {
        return client;
    }
}
