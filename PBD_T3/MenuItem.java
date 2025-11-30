/**
 * Kelas abstrak MenuItem yang menjadi kelas dasar untuk semua menu item dalam restoran.
 * Mengimplementasikan konsep abstraksi dan encapsulation.
 */
public abstract class MenuItem {
    // Atribut private untuk implementasi encapsulation
    private String nama;
    private double harga;
    private String kategori;
    private static int counterId = 1;
    private final int id;

    /**
     * Constructor untuk kelas MenuItem
     * @param nama Nama menu item
     * @param harga Harga menu item
     * @param kategori Kategori menu item
     */
    public MenuItem(String nama, double harga, String kategori) {
        this.nama = nama;
        this.harga = harga;
        this.kategori = kategori;
        this.id = counterId++;
    }

    // Getter methods untuk encapsulation
    public String getNama() {
        return nama;
    }

    public double getHarga() {
        return harga;
    }

    public String getKategori() {
        return kategori;
    }

    public int getId() {
        return id;
    }

    // Setter methods dengan validasi
    public void setNama(String nama) {
        if (nama != null && !nama.trim().isEmpty()) {
            this.nama = nama;
        } else {
            throw new IllegalArgumentException("Nama tidak boleh kosong");
        }
    }

    public void setHarga(double harga) {
        if (harga >= 0) {
            this.harga = harga;
        } else {
            throw new IllegalArgumentException("Harga tidak boleh negatif");
        }
    }

    public void setKategori(String kategori) {
        if (kategori != null && !kategori.trim().isEmpty()) {
            this.kategori = kategori;
        } else {
            throw new IllegalArgumentException("Kategori tidak boleh kosong");
        }
    }

    /**
     * Metode abstrak yang harus diimplementasikan oleh kelas turunan
     * untuk menampilkan informasi spesifik tentang menu item
     */
    public abstract void tampilMenu();

    /**
     * Metode untuk menampilkan informasi dasar menu item
     */
    public void tampilInfoDasar() {
        System.out.printf("ID: %d | %s - Rp%.2f (%s)%n", id, nama, harga, kategori);
    }

    /**
     * Override metode toString untuk representasi string dari objek
     */
    @Override
    public String toString() {
        return String.format("ID: %d | %s - Rp%.2f (%s)", id, nama, harga, kategori);
    }

    /**
     * Metode untuk menghasilkan format penyimpanan ke file
     */
    public String toFileString() {
        return String.format("%s|%s|%.2f|%s",
            this.getClass().getSimpleName(), nama, harga, kategori);
    }
}