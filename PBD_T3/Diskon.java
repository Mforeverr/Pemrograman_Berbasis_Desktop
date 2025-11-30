/**
 * Kelas Diskon yang merupakan turunan dari MenuItem.
 * Digunakan untuk menerapkan diskon khusus pada menu.
 * Mengimplementasikan inheritance dan polymorphism.
 */
public class Diskon extends MenuItem {
    // Atribut khusus untuk kelas Diskon
    private double persentaseDiskon;
    private double minimalPembelian;
    private String syaratDiskon;
    private boolean aktif;

    /**
     * Constructor untuk kelas Diskon
     * @param nama Nama diskon
     * @param persentaseDiskon Persentase diskon (dalam desimal, 0.1 = 10%)
     * @param minimalPembelian Minimal pembelian untuk mendapatkan diskon
     * @param syaratDiskon Syarat tambahan untuk diskon
     */
    public Diskon(String nama, double persentaseDiskon, double minimalPembelian, String syaratDiskon) {
        super(nama, 0.0, "diskon");
        this.persentaseDiskon = persentaseDiskon;
        this.minimalPembelian = minimalPembelian;
        this.syaratDiskon = syaratDiskon;
        this.aktif = true;
    }

    // Getter dan Setter untuk atribut khusus Diskon
    public double getPersentaseDiskon() {
        return persentaseDiskon;
    }

    public void setPersentaseDiskon(double persentaseDiskon) {
        if (persentaseDiskon >= 0 && persentaseDiskon <= 1) {
            this.persentaseDiskon = persentaseDiskon;
        } else {
            throw new IllegalArgumentException("Persentase diskon harus antara 0 dan 1");
        }
    }

    public double getMinimalPembelian() {
        return minimalPembelian;
    }

    public void setMinimalPembelian(double minimalPembelian) {
        if (minimalPembelian >= 0) {
            this.minimalPembelian = minimalPembelian;
        } else {
            throw new IllegalArgumentException("Minimal pembelian tidak boleh negatif");
        }
    }

    public String getSyaratDiskon() {
        return syaratDiskon;
    }

    public void setSyaratDiskon(String syaratDiskon) {
        this.syaratDiskon = syaratDiskon;
    }

    public boolean isAktif() {
        return aktif;
    }

    public void setAktif(boolean aktif) {
        this.aktif = aktif;
    }

    /**
     * Implementasi metode abstrak tampilMenu() dari kelas MenuItem
     * Mengimplementasikan konsep polymorphism
     */
    @Override
    public void tampilMenu() {
        System.out.println("=== INFO DISKON ===");
        System.out.println("Nama Diskon: " + getNama());
        System.out.println("Persentase: " + (persentaseDiskon * 100) + "%");
        System.out.println("Minimal Pembelian: Rp" + String.format("%.2f", minimalPembelian));
        System.out.println("Syarat: " + syaratDiskon);
        System.out.println("Status: " + (aktif ? "Aktif" : "Tidak Aktif"));
        System.out.println("-------------------");
    }

    /**
     * Metode untuk menghitung jumlah diskon
     * @param totalBelanja Total belanja sebelum diskon
     * @return Jumlah diskon yang diberikan
     */
    public double hitungDiskon(double totalBelanja) {
        if (!aktif || totalBelanja < minimalPembelian) {
            return 0;
        }
        return totalBelanja * persentaseDiskon;
    }

    /**
     * Metode untuk memeriksa apakah diskon berlaku
     * @param totalBelanja Total belanja
     * @return true jika diskon berlaku
     */
    public boolean diskonBerlaku(double totalBelanja) {
        return aktif && totalBelanja >= minimalPembelian;
    }

    /**
     * Metode untuk mendapatkan pesan diskon
     * @return Pesan informatif tentang diskon
     */
    public String getPesanDiskon() {
        if (!aktif) {
            return getNama() + " - Tidak Aktif";
        }
        return String.format("%s - %.0f%% OFF (Minimal: Rp%.2f)",
            getNama(), persentaseDiskon * 100, minimalPembelian);
    }

    /**
     * Override metode toString untuk representasi spesifik diskon
     */
    @Override
    public String toString() {
        return String.format("ID:%d [DISKON] %s - %.0f%% (Min: Rp%.2f) - %s",
            getId(), getNama(), persentaseDiskon * 100, minimalPembelian,
            aktif ? "Aktif" : "Tidak Aktif");
    }

    /**
     * Override metode toFileString untuk penyimpanan ke file
     */
    @Override
    public String toFileString() {
        return String.format("Diskon|%s|0.0|%s|%.2f|%.2f|%s|%s",
            getNama(), getKategori(), persentaseDiskon, minimalPembelian,
            syaratDiskon, aktif);
    }

    /**
     * Metode untuk mengaktifkan diskon
     */
    public void aktivasiDiskon() {
        this.aktif = true;
    }

    /**
     * Metode untuk menonaktifkan diskon
     */
    public void nonAktifkanDiskon() {
        this.aktif = false;
    }

    /**
     * Metode untuk mengubah persentase diskon dengan validasi
     * @param persentaseBaru Persentase diskon baru (dalam persen)
     */
    public void ubahPersentaseDiskon(double persentaseBaru) {
        if (persentaseBaru >= 0 && persentaseBaru <= 100) {
            this.persentaseDiskon = persentaseBaru / 100;
        } else {
            throw new IllegalArgumentException("Persentase diskon harus antara 0 dan 100");
        }
    }
}
