/**
 * Kelas Menu untuk merepresentasikan menu makanan dan minuman di restoran
 */
public class Menu {
    private int id;
    private String nama;
    private double harga;
    private String kategori; // "makanan" atau "minuman"

    public Menu(int id, String nama, double harga, String kategori) {
        this.id = id;
        this.nama = nama;
        this.harga = harga;
        this.kategori = kategori;
    }

    // Getter methods
    public int getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    public double getHarga() {
        return harga;
    }

    public String getKategori() {
        return kategori;
    }

    // Setter methods
    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setHarga(double harga) {
        this.harga = harga;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    @Override
    public String toString() {
        return String.format("%d. %s - Rp%.2f (%s)", id, nama, harga, kategori);
    }
}