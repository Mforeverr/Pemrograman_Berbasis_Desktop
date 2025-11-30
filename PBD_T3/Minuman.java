/**
 * Kelas Minuman yang merupakan turunan dari MenuItem.
 * Mengimplementasikan inheritance dan polymorphism.
 */
public class Minuman extends MenuItem {
    // Atribut khusus untuk kelas Minuman
    private String jenisMinuman; // panas, dingin, hangat
    private String ukuran; // small, medium, large
    private boolean adaGula;

    /**
     * Constructor lengkap untuk kelas Minuman
     * @param nama Nama minuman
     * @param harga Harga minuman
     * @param jenisMinuman Jenis minuman (panas/dingin/hangat)
     * @param ukuran Ukuran minuman
     * @param adaGula Status keberadaan gula
     */
    public Minuman(String nama, double harga, String jenisMinuman, String ukuran, boolean adaGula) {
        super(nama, harga, "minuman");
        this.jenisMinuman = jenisMinuman;
        this.ukuran = ukuran;
        this.adaGula = adaGula;
    }

    /**
     * Constructor untuk kelas Minuman (default: medium, ada gula)
     * @param nama Nama minuman
     * @param harga Harga minuman
     * @param jenisMinuman Jenis minuman
     */
    public Minuman(String nama, double harga, String jenisMinuman) {
        this(nama, harga, jenisMinuman, "medium", true);
    }

    // Getter dan Setter untuk atribut khusus Minuman
    public String getJenisMinuman() {
        return jenisMinuman;
    }

    public void setJenisMinuman(String jenisMinuman) {
        this.jenisMinuman = jenisMinuman;
    }

    public String getUkuran() {
        return ukuran;
    }

    public void setUkuran(String ukuran) {
        this.ukuran = ukuran;
    }

    public boolean isAdaGula() {
        return adaGula;
    }

    public void setAdaGula(boolean adaGula) {
        this.adaGula = adaGula;
    }

    /**
     * Implementasi metode abstrak tampilMenu() dari kelas MenuItem
     * Mengimplementasikan konsep polymorphism
     */
    @Override
    public void tampilMenu() {
        System.out.println("=== DETAIL MINUMAN ===");
        tampilInfoDasar();
        System.out.println("Jenis: " + jenisMinuman);
        System.out.println("Ukuran: " + ukuran);
        System.out.println("Gula: " + (adaGula ? "Ya" : "Tidak"));
        System.out.println("------------------------");
    }

    /**
     * Override metode toString untuk representasi spesifik minuman
     */
    @Override
    public String toString() {
        return String.format("ID:%d [MINUMAN] %s - Rp%.2f (%s | %s | %s)",
            getId(), getNama(), getHarga(), jenisMinuman, ukuran, adaGula ? "Gula" : "Tanpa Gula");
    }

    /**
     * Override metode toFileString untuk penyimpanan ke file
     */
    @Override
    public String toFileString() {
        return String.format("Minuman|%s|%.2f|%s|%s|%s|%s",
            getNama(), getHarga(), getKategori(), jenisMinuman, ukuran, adaGula);
    }

    /**
     * Metode untuk memeriksa apakah minuman panas
     */
    public boolean isPanas() {
        return jenisMinuman.equalsIgnoreCase("panas") || jenisMinuman.equalsIgnoreCase("hangat");
    }

    /**
     * Metode untuk memeriksa apakah minuman sehat (tanpa gula)
     */
    public boolean isSehat() {
        return !adaGula;
    }

    /**
     * Metode untuk menyesuaikan harga berdasarkan ukuran
     */
    public double getHargaDisesuaikan() {
        double hargaAsli = getHarga();
        switch (ukuran.toLowerCase()) {
            case "small":
                return hargaAsli * 0.8;
            case "large":
                return hargaAsli * 1.2;
            default:
                return hargaAsli; // medium
        }
    }

    /**
     * Metode untuk mendapatkan deskripsi lengkap minuman
     */
    public String getDeskripsiLengkap() {
        return String.format("%s - %s (%s | %s)",
            getNama(), jenisMinuman, ukuran, adaGula ? "Gula" : "Tanpa Gula");
    }
}
