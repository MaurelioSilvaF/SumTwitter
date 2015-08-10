package rmservicos.net.rmservicostwitterapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;

/**
 * Created by Maurelio on 08/08/2015.
 * Class to save the twitts with coordinates to show in map
 */
public class Localizacoes implements Serializable {

    private long nId;
    private double nLongitude;
    private double nLatitude;
    private String sAutor;
    private String sDescricao;
    private String sURLImagem;
    private Bitmap bitmap;

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public Localizacoes(long nId, double nLongitude, double nLatitude, String sAutor, String sDescricao, String sURLImagem) {
        this.nId = nId;
        this.nLongitude = nLongitude;
        this.nLatitude = nLatitude;
        this.sAutor = sAutor;
        this.sDescricao = sDescricao;
        try {
            setsURLImagem(sURLImagem);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public double getnLongitude() {
        return nLongitude;
    }

    public void setnLongitude(double nLongitude) {
        this.nLongitude = nLongitude;
    }

    public double getnLatitude() {
        return nLatitude;
    }

    public void setnLatitude(double nLatitude) {
        this.nLatitude = nLatitude;
    }

    public String getsAutor() {
        return sAutor;
    }

    public void setsAutor(String sAutor) {
        this.sAutor = sAutor;
    }

    public String getsDescricao() {
        return sDescricao;
    }

    public void setsDescricao(String sDescricao) {
        this.sDescricao = sDescricao;
    }

    public long getnId() {
        return nId;
    }

    public void setnId(long nId) {
        this.nId = nId;
    }

    public String getsURLImagem() { return sURLImagem; }

    public void setsURLImagem(String sURLImagem) throws IOException {
        this.sURLImagem = sURLImagem;
    }
}
