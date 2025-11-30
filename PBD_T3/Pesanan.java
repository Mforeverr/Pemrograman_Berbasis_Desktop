import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.DecimalFormat;

/**
 * Kelas Pesanan untuk mencatat pesanan pelanggan.
 * Mengimplementasikan konsep encapsulation dan pengelolaan data pesanan.
 */
public class Pesanan {
    // Atribut private untuk encapsulation
    private ArrayList<ItemPesanan> itemPesanan;
    private String namaPelanggan;
    private Date tanggalPesanan;
    private String noMeja;
    private static int counterIdPesanan = 1;
    private final int idPesanan;
    private double pajak;
    private double biayaPelayanan;

    // Konstanta untuk biaya
    public static final double PAJAK_DEFAULT = 0.10; // 10%
    public static final double BIAYA_PELAYANAN_DEFAULT = 20000.0;

    /**
     * Inner class ItemPesanan untuk menyimpan detail item yang dipesan
     */
    public static class ItemPesanan {
        private MenuItem menuItem;
        private int jumlah;
        private double hargaSatuan;
        private String catatan;

        public ItemPesanan(MenuItem menuItem, int jumlah, String catatan) {
            this.menuItem = menuItem;
            this.jumlah = jumlah;
            this.hargaSatuan = menuItem.getHarga();
            this.catatan = catatan;
        }

        public ItemPesanan(MenuItem menuItem, int jumlah) {
            this(menuItem, jumlah, "");
        }

        // Getter methods
        public MenuItem getMenuItem() {
            return menuItem;
        }

        public int getJumlah() {
            return jumlah;
        }

        public double getHargaSatuan() {
            return hargaSatuan;
        }

        public String getCatatan() {
            return catatan;
        }

        public double getTotalHarga() {
            double harga = menuItem.getHarga();

            // Jika minuman, hitung harga disesuaikan ukuran
            if (menuItem instanceof Minuman) {
                harga = ((Minuman) menuItem).getHargaDisesuaikan();
            }

            return harga * jumlah;
        }

        public void setJumlah(int jumlah) {
            if (jumlah > 0) {
                this.jumlah = jumlah;
            }
        }

        public void setCatatan(String catatan) {
            this.catatan = catatan;
        }

        @Override
        public String toString() {
            return String.format("%s - %d x Rp%.2f = Rp%.2f %s",
                menuItem.getNama(), jumlah, getHargaSatuan(), getTotalHarga(),
                catatan.isEmpty() ? "" : "(" + catatan + ")");
        }
    }

    /**
     * Constructor untuk kelas Pesanan
     * @param namaPelanggan Nama pelanggan
     * @param noMeja Nomor meja
     */
    public Pesanan(String namaPelanggan, String noMeja) {
        this.namaPelanggan = namaPelanggan;
        this.noMeja = noMeja;
        this.itemPesanan = new ArrayList<>();
        this.tanggalPesanan = new Date();
        this.idPesanan = counterIdPesanan++;
        this.pajak = PAJAK_DEFAULT;
        this.biayaPelayanan = BIAYA_PELAYANAN_DEFAULT;
    }

    /**
     * Constructor default untuk kelas Pesanan
     */
    public Pesanan() {
        this("Pelanggan", "Take Away");
    }

    // Getter methods
    public String getNamaPelanggan() {
        return namaPelanggan;
    }

    public String getNoMeja() {
        return noMeja;
    }

    public Date getTanggalPesanan() {
        return new Date(tanggalPesanan.getTime()); // Return copy
    }

    public int getIdPesanan() {
        return idPesanan;
    }

    public double getPajak() {
        return pajak;
    }

    public double getBiayaPelayanan() {
        return biayaPelayanan;
    }

    public ArrayList<ItemPesanan> getItemPesanan() {
        return new ArrayList<>(itemPesanan); // Return copy
    }

    // Setter methods
    public void setNamaPelanggan(String namaPelanggan) {
        this.namaPelanggan = namaPelanggan;
    }

    public void setNoMeja(String noMeja) {
        this.noMeja = noMeja;
    }

    public void setPajak(double pajak) {
        if (pajak >= 0 && pajak <= 1) {
            this.pajak = pajak;
        }
    }

    public void setBiayaPelayanan(double biayaPelayanan) {
        if (biayaPelayanan >= 0) {
            this.biayaPelayanan = biayaPelayanan;
        }
    }

    /**
     * Metode untuk menambahkan item ke pesanan
     * @param menuItem MenuItem yang akan ditambahkan
     * @param jumlah Jumlah item
     */
    public void tambahItem(MenuItem menuItem, int jumlah) {
        if (menuItem != null && jumlah > 0) {
            // Cek apakah item sudah ada dalam pesanan
            for (ItemPesanan item : itemPesanan) {
                if (item.getMenuItem().getId() == menuItem.getId()) {
                    // Update jumlah jika item sudah ada
                    item.setJumlah(item.getJumlah() + jumlah);
                    System.out.println("Jumlah item diperbarui: " + item);
                    return;
                }
            }
            // Tambah item baru jika belum ada
            itemPesanan.add(new ItemPesanan(menuItem, jumlah));
            System.out.println("Item berhasil ditambahkan: " + menuItem.getNama() + " (" + jumlah + "x)");
        } else {
            throw new IllegalArgumentException("MenuItem tidak valid atau jumlah harus > 0");
        }
    }

    /**
     * Metode untuk menambahkan item dengan catatan
     * @param menuItem MenuItem yang akan ditambahkan
     * @param jumlah Jumlah item
     * @param catatan Catatan khusus
     */
    public void tambahItem(MenuItem menuItem, int jumlah, String catatan) {
        if (menuItem != null && jumlah > 0) {
            itemPesanan.add(new ItemPesanan(menuItem, jumlah, catatan));
            System.out.println("Item berhasil ditambahkan: " + menuItem.getNama() + " (" + jumlah + "x)");
        } else {
            throw new IllegalArgumentException("MenuItem tidak valid atau jumlah harus > 0");
        }
    }

    /**
     * Metode untuk menghapus item dari pesanan
     * @param idMenuItem ID MenuItem yang akan dihapus
     * @return true jika berhasil dihapus
     */
    public boolean hapusItem(int idMenuItem) {
        for (int i = 0; i < itemPesanan.size(); i++) {
            if (itemPesanan.get(i).getMenuItem().getId() == idMenuItem) {
                ItemPesanan itemDihapus = itemPesanan.remove(i);
                System.out.println("Item berhasil dihapus: " + itemDihapus.getMenuItem().getNama());
                return true;
            }
        }
        System.out.println("Item dengan ID " + idMenuItem + " tidak ditemukan dalam pesanan");
        return false;
    }

    /**
     * Metode untuk mengubah jumlah item
     * @param idMenuItem ID MenuItem yang akan diubah
     * @param jumlahBaru Jumlah baru
     * @return true jika berhasil diubah
     */
    public boolean ubahJumlahItem(int idMenuItem, int jumlahBaru) {
        if (jumlahBaru <= 0) {
            return hapusItem(idMenuItem);
        }

        for (ItemPesanan item : itemPesanan) {
            if (item.getMenuItem().getId() == idMenuItem) {
                item.setJumlah(jumlahBaru);
                System.out.println("Jumlah item berhasil diubah: " + item);
                return true;
            }
        }
        System.out.println("Item dengan ID " + idMenuItem + " tidak ditemukan dalam pesanan");
        return false;
    }

    /**
     * Metode untuk menghitung subtotal (total sebelum pajak dan biaya lainnya)
     * @return Subtotal pesanan
     */
    public double hitungSubtotal() {
        double subtotal = 0;
        for (ItemPesanan item : itemPesanan) {
            subtotal += item.getTotalHarga();
        }
        return subtotal;
    }

    /**
     * Metode untuk menghitung total pajak
     * @return Jumlah pajak
     */
    public double hitungPajak() {
        return hitungSubtotal() * pajak;
    }

    /**
     * Metode untuk menghitung total akhir pesanan
     * @return Total akhir pesanan
     */
    public double hitungTotalAkhir() {
        return hitungSubtotal() + hitungPajak() + biayaPelayanan;
    }

    /**
     * Metode untuk menerapkan diskon
     * @param diskon Diskon yang akan diterapkan
     * @return Jumlah diskon yang diberikan
     */
    public double terapkanDiskon(Diskon diskon) {
        if (diskon != null && diskon.diskonBerlaku(hitungTotalAkhir())) {
            double jumlahDiskon = diskon.hitungDiskon(hitungTotalAkhir());
            return jumlahDiskon;
        }
        return 0;
    }

    /**
     * Metode untuk menampilkan detail pesanan
     */
    public void tampilDetailPesanan() {
        DecimalFormat df = new DecimalFormat("#,##0.00");
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        System.out.println("\n" + "=".repeat(60));
        System.out.println("                    DETAIL PESANAN");
        System.out.println("=".repeat(60));
        System.out.println("ID Pesanan: " + idPesanan);
        System.out.println("Nama Pelanggan: " + namaPelanggan);
        System.out.println("Nomor Meja: " + noMeja);
        System.out.println("Tanggal: " + sdf.format(tanggalPesanan));
        System.out.println("-".repeat(60));
        System.out.println("ITEM PESANAN:");

        if (itemPesanan.isEmpty()) {
            System.out.println("  Belum ada item yang dipesan");
        } else {
            for (ItemPesanan item : itemPesanan) {
                System.out.println("  " + item);
            }
        }

        System.out.println("-".repeat(60));
        System.out.printf("%-45s: Rp %s%n", "Subtotal", df.format(hitungSubtotal()));
        System.out.printf("%-45s: Rp %s%n", "Pajak (" + (pajak * 100) + "%)", df.format(hitungPajak()));
        System.out.printf("%-45s: Rp %s%n", "Biaya Pelayanan", df.format(biayaPelayanan));
        System.out.println("=".repeat(60));
        System.out.printf("%-45s: Rp %s%n", "TOTAL AKHIR", df.format(hitungTotalAkhir()));
        System.out.println("=".repeat(60));
    }

    /**
     * Metode untuk mencetak struk lengkap dengan diskon
     * @param diskon Daftar diskon yang tersedia
     */
    public void cetakStruk(ArrayList<Diskon> diskon) {
        DecimalFormat df = new DecimalFormat("#,##0.00");
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        double subtotal = hitungSubtotal();
        double pajak = hitungPajak();
        double totalSebelumDiskon = subtotal + pajak + biayaPelayanan;
        double totalDiskon = 0;

        // Cek diskon yang berlaku
        Diskon diskonBerlaku = null;
        for (Diskon d : diskon) {
            if (d.diskonBerlaku(totalSebelumDiskon)) {
                double jumlahDiskon = d.hitungDiskon(totalSebelumDiskon);
                if (jumlahDiskon > totalDiskon) {
                    totalDiskon = jumlahDiskon;
                    diskonBerlaku = d;
                }
            }
        }

        double totalAkhir = totalSebelumDiskon - totalDiskon;

        System.out.println("\n" + "=".repeat(65));
        System.out.println("                       STRUK PEMBAYARAN");
        System.out.println("=".repeat(65));
        System.out.println("RESTORAN: " + namaPelanggan);
        System.out.println("ID Pesanan: #" + idPesanan);
        System.out.println("Tanggal: " + sdf.format(tanggalPesanan));
        System.out.println("Meja: " + noMeja);
        System.out.println("-".repeat(65));
        System.out.println("ITEM YANG DIPESAN:");

        for (ItemPesanan item : itemPesanan) {
            System.out.printf("  %-35s %2d x %9s = %9s%n",
                item.getMenuItem().getNama(),
                item.getJumlah(),
                "Rp" + df.format(item.getHargaSatuan()),
                "Rp" + df.format(item.getTotalHarga()));
        }

        System.out.println("-".repeat(65));
        System.out.printf("%-45s: %9s%n", "SUBTOTAL", "Rp" + df.format(subtotal));
        System.out.printf("%-45s: %9s%n", "PAJAK (" + (this.pajak * 100) + "%)", "Rp" + df.format(pajak));
        System.out.printf("%-45s: %9s%n", "BIAYA PELAYANAN", "Rp" + df.format(biayaPelayanan));

        if (diskonBerlaku != null) {
            System.out.printf("%-45s: %9s%n", "DISKON (" + diskonBerlaku.getPesanDiskon() + ")",
                              "-Rp" + df.format(totalDiskon));
        }

        System.out.println("=".repeat(65));
        System.out.printf("%-45s: %9s%n", "TOTAL PEMBAYARAN", "Rp" + df.format(totalAkhir));
        System.out.println("=".repeat(65));

        // Info tambahan
        if (diskonBerlaku != null) {
            System.out.println("\nINFO DISKON: " + diskonBerlaku.getPesanDiskon());
        }
    }

    /**
     * Metode untuk memeriksa apakah pesanan kosong
     * @return true jika pesanan kosong
     */
    public boolean isPesananKosong() {
        return itemPesanan.isEmpty();
    }

    /**
     * Metode untuk mendapatkan jumlah item total
     * @return Total jumlah semua item
     */
    public int getTotalJumlahItem() {
        int total = 0;
        for (ItemPesanan item : itemPesanan) {
            total += item.getJumlah();
        }
        return total;
    }

    /**
     * Metode untuk mengosongkan pesanan
     */
    public void kosongkanPesanan() {
        itemPesanan.clear();
        System.out.println("Pesanan telah dikosongkan.");
    }

    /**
     * Override metode toString
     */
    @Override
    public String toString() {
        return String.format("Pesanan[ID=%d, Pelanggan=%s, Total=%d item, TotalHarga=Rp%.2f]",
            idPesanan, namaPelanggan, getTotalJumlahItem(), hitungTotalAkhir());
    }
}