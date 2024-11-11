public class Place {

    private String name  ;
    private int    tokens;

    // public Place(String name) {
    //     this.name = name;
    // }

    public Place(String name, int tokens) {
        this.name   = name  ;
        this.tokens = tokens;
    }

    public int getTokens() {
        return tokens;
    }
    public void setTokens(int tokens) {
        this.tokens = tokens;
    }

}