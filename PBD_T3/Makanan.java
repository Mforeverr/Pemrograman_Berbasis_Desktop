/**
 * Kelas Makanan yang merupakan turunan dari MenuItem.
 * Mengimplementasikan inheritance dan polymorphism.
 */
public class Makanan extends MenuItem {
    // Atribut khusus untuk kelas Makanan
    private String jenisMakanan; // pokok, pendamping, penutup, dll
    private String tingkatKepedasan; // tidak pedas, sedang, pedas, sangat pedas

    /**
     * Constructor untuk kelas Makanan
     * @param nama Nama makanan
     * @param harga Harga makanan
     * @param jenisMakanan Jenis makanan
     * @param tingkatKepedasan Tingkat kepedasan
     */
    public Makanan(String nama, double harga, String jenisMakanan, String tingkatKepedasan) {
        super(nama, harga, "makanan");
        this.jenisMakanan = jenisMakanan;
        this.tingkatKepedasan = tingkatKepedasan;
    }

    /**
     * Constructor untuk kelas Makanan (tanpa tingkat kepedasan)
     * @param nama Nama makanan
     * @param harga Harga makanan
     * @param jenisMakanan Jenis makanan
     */
    public Makanan(String nama, double harga, String jenisMakanan) {
        this(nama, harga, jenisMakanan, "tidak pedas");
    }

    // Getter dan Setter untuk atribut khusus Makanan
    public String getJenisMakanan() {
        return jenisMakanan;
    }

    public void setJenisMakanan(String jenisMakanan) {
        this.jenisMakanan = jenisMakanan;
    }

    public String getTingkatKepedasan() {
        return tingkatKepedasan;
    }

    public void setTingkatKepedasan(String tingkatKepedasan) {
        this.tingkatKepedasan = tingkatKepedasan;
    }

    /**
     * Implementasi metode abstrak tampilMenu() dari kelas MenuItem
     * Mengimplementasikan konsep polymorphism
     */
    @Override
    public void tampilMenu() {
        System.out.println("=== DETAIL MAKANAN ===");
        tampilInfoDasar();
        System.out.println("Jenis Makanan: " + jenisMakanan);
        System.out.println("Tingkat Kepedasan: " + tingkatKepedasan);
        System.out.println("------------------------");
    }

    /**
     * Override metode toString untuk representasi spesifik makanan
     */
    @Override
    public String toString() {
        return String.format("ID:%d [MAKANAN] %s - Rp%.2f (%s | %s)",
            getId(), getNama(), getHarga(), jenisMakanan, tingkatKepedasan);
    }

    /**
     * Override metode toFileString untuk penyimpanan ke file
     */
    @Override
    public String toFileString() {
        return String.format("Makanan|%s|%.2f|%s|%s|%s",
            getNama(), getHarga(), getKategori(), jenisMakanan, tingkatKepedasan);
    }

    /**
     * Metode untuk memeriksa apakah makanan pedas
     */
    public boolean isPedas() {
        return !tingkatKepedasan.equals("tidak pedas");
    }

    /**
     * Metode untuk mendapatkan deskripsi lengkap makanan
     */
    public String getDeskripsiLengkap() {
        return String.format("%s - %s (%s)",
            getNama(), jenisMakanan, tingkatKepedasan);
    }
}
