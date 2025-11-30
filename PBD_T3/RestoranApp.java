import java.util.Scanner;
import java.util.ArrayList;
import java.util.InputMismatchException;

/**
 * Kelas utama aplikasi manajemen restoran.
 * Mengimplementasikan semua konsep OOP yang telah dipelajari:
 * - Abstraksi (kelas abstrak MenuItem)
 * - Inheritance (Makanan, Minuman, Diskon turunan dari MenuItem)
 * - Encapsulation (private atribut dengan public methods)
 * - Polymorphism (override method tampilMenu())
 * - Exception Handling (try-catch untuk I/O dan validasi)
 * - I/O Operations (FileHandler untuk file processing)
 * - Struktur keputusan dan pengulangan
 * - Array dan ArrayList
 */
public class RestoranApp {
    private static Scanner scanner = new Scanner(System.in);
    private static MenuRestoran menuRestoran;
    private static ArrayList<Pesanan> daftarPesanan;
    private static Pesanan pesananAktif;

    public static void main(String[] args) {
        try {
            // Inisialisasi aplikasi
            System.out.println("=".repeat(60));
            System.out.println("    APLIKASI MANAJEMEN RESTORAN BERBASIS OBJEK");
            System.out.println("=".repeat(60));
            System.out.println("Memuat data menu dari file...");

            // Muat menu dari file
            menuRestoran = FileHandler.muatMenuDariFile();
            daftarPesanan = new ArrayList<>();
            pesananAktif = null;

            System.out.println("Aplikasi berhasil dimuat!");
            System.out.println("Menu tersedia: " + menuRestoran.getJumlahMenu() + " item");

            // Tampilkan menu utama
            menuUtama();

        } catch (Exception e) {
            System.err.println("Error saat memulai aplikasi: " + e.getMessage());
            System.out.println("Aplikasi akan dimulai dengan menu default...");

            // Fallback ke menu default
            menuRestoran = new MenuRestoran("Restoran Saya");
            buatMenuDefault();
            daftarPesanan = new ArrayList<>();
            pesananAktif = null;
            menuUtama();
        }
    }

    /**
     * Menu utama aplikasi
     */
    private static void menuUtama() {
        while (true) {
            System.out.println("\n" + "=".repeat(60));
            System.out.println("                    MENU UTAMA");
            System.out.println("=".repeat(60));
            System.out.println("1. Kelola Menu Restoran");
            System.out.println("2. Buat Pesanan Baru");
            System.out.println("3. Kelola Pesanan");
            System.out.println("4. Tampilkan Semua Menu");
            System.out.println("5. Manajemen File");
            System.out.println("6. Statistik Restoran");
            System.out.println("7. Keluar");
            System.out.print("Pilih menu (1-7): ");

            try {
                int pilihan = getIntInput(1, 7);

                switch (pilihan) {
                    case 1:
                        menuKelolaMenu();
                        break;
                    case 2:
                        buatPesananBaru();
                        break;
                    case 3:
                        menuKelolaPesanan();
                        break;
                    case 4:
                        menuRestoran.tampilkanSemuaMenu();
                        break;
                    case 5:
                        menuManajemenFile();
                        break;
                    case 6:
                        tampilkanStatistik();
                        break;
                    case 7:
                        keluarAplikasi();
                        return;
                }
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
                scanner.nextLine(); // Clear buffer
            }
        }
    }

    /**
     * Menu untuk mengelola menu restoran
     */
    private static void menuKelolaMenu() {
        while (true) {
            System.out.println("\n" + "-".repeat(50));
            System.out.println("              KELOLA MENU RESTORAN");
            System.out.println("-".repeat(50));
            System.out.println("1. Tambah Menu Baru");
            System.out.println("2. Tambah Makanan Baru");
            System.out.println("3. Tambah Minuman Baru");
            System.out.println("4. Tambah Diskon Baru");
            System.out.println("5. Ubah Menu");
            System.out.println("6. Hapus Menu");
            System.out.println("7. Tampilkan Detail Menu");
            System.out.println("8. Urutkan Menu");
            System.out.println("9. Kembali ke Menu Utama");
            System.out.print("Pilih menu (1-9): ");

            try {
                int pilihan = getIntInput(1, 9);

                switch (pilihan) {
                    case 1:
                        tambahMenuBaru();
                        break;
                    case 2:
                        tambahMakananBaru();
                        break;
                    case 3:
                        tambahMinumanBaru();
                        break;
                    case 4:
                        tambahDiskonBaru();
                        break;
                    case 5:
                        ubahMenu();
                        break;
                    case 6:
                        hapusMenu();
                        break;
                    case 7:
                        tampilkanDetailMenu();
                        break;
                    case 8:
                        urutkanMenu();
                        break;
                    case 9:
                        return;
                }
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
                scanner.nextLine();
            }
        }
    }

    /**
     * Metode untuk menambah menu baru
     */
    private static void tambahMenuBaru() {
        System.out.println("\n=== TAMBAH MENU BARU ===");

        scanner.nextLine(); // Clear buffer
        System.out.print("Nama menu: ");
        String nama = scanner.nextLine();

        System.out.print("Harga: ");
        double harga = getDoubleInput(0, Double.MAX_VALUE);

        System.out.print("Kategori (makanan/minuman/diskon): ");
        String kategori = scanner.nextLine().toLowerCase();

        MenuItem itemBaru;
        switch (kategori) {
            case "makanan":
                System.out.print("Jenis makanan: ");
                String jenisMakanan = scanner.nextLine();
                System.out.print("Tingkat kepedasan: ");
                String tingkatKepedasan = scanner.nextLine();
                itemBaru = new Makanan(nama, harga, jenisMakanan, tingkatKepedasan);
                break;
            case "minuman":
                System.out.print("Jenis minuman (panas/dingin/hangat): ");
                String jenisMinuman = scanner.nextLine();
                System.out.print("Ukuran (small/medium/large): ");
                String ukuran = scanner.nextLine();
                System.out.print("Ada gula (true/false): ");
                boolean adaGula = scanner.nextBoolean();
                scanner.nextLine(); // Clear buffer
                itemBaru = new Minuman(nama, harga, jenisMinuman, ukuran, adaGula);
                break;
            case "diskon":
                System.out.print("Persentase diskon (0-1): ");
                double persentase = scanner.nextDouble();
                System.out.print("Minimal pembelian: ");
                double minimal = scanner.nextDouble();
                scanner.nextLine(); // Clear buffer
                System.out.print("Syarat diskon: ");
                String syarat = scanner.nextLine();
                itemBaru = new Diskon(nama, persentase, minimal, syarat);
                break;
            default:
                throw new IllegalArgumentException("Kategori tidak valid");
        }

        menuRestoran.tambahMenuItem(itemBaru);
        System.out.println("Menu berhasil ditambahkan!");
    }

    /**
     * Metode untuk menambah makanan baru
     */
    private static void tambahMakananBaru() {
        System.out.println("\n=== TAMBAH MAKANAN BARU ===");

        scanner.nextLine();
        System.out.print("Nama makanan: ");
        String nama = scanner.nextLine();

        System.out.print("Harga: ");
        double harga = getDoubleInput(0, Double.MAX_VALUE);

        System.out.print("Jenis makanan (pokok/pendamping/penutup): ");
        String jenis = scanner.nextLine();

        System.out.print("Tingkat kepedasan: ");
        String kepedasan = scanner.nextLine();

        Makanan makananBaru = new Makanan(nama, harga, jenis, kepedasan);
        menuRestoran.tambahMenuItem(makananBaru);
        System.out.println("Makanan berhasil ditambahkan!");
    }

    /**
     * Metode untuk menambah minuman baru
     */
    private static void tambahMinumanBaru() {
        System.out.println("\n=== TAMBAH MINUMAN BARU ===");

        scanner.nextLine();
        System.out.print("Nama minuman: ");
        String nama = scanner.nextLine();

        System.out.print("Harga: ");
        double harga = getDoubleInput(0, Double.MAX_VALUE);

        System.out.print("Jenis minuman (panas/dingin/hangat): ");
        String jenis = scanner.nextLine();

        System.out.print("Ukuran (small/medium/large): ");
        String ukuran = scanner.nextLine();

        System.out.print("Ada gula (true/false): ");
        boolean adaGula = scanner.nextBoolean();
        scanner.nextLine();

        Minuman minumanBaru = new Minuman(nama, harga, jenis, ukuran, adaGula);
        menuRestoran.tambahMenuItem(minumanBaru);
        System.out.println("Minuman berhasil ditambahkan!");
    }

    /**
     * Metode untuk menambah diskon baru
     */
    private static void tambahDiskonBaru() {
        System.out.println("\n=== TAMBAH DISKON BARU ===");

        scanner.nextLine();
        System.out.print("Nama diskon: ");
        String nama = scanner.nextLine();

        System.out.print("Persentase diskon (0-1, contoh: 0.1 untuk 10%): ");
        double persentase = getDoubleInput(0, 1);

        System.out.print("Minimal pembelian: ");
        double minimal = getDoubleInput(0, Double.MAX_VALUE);

        System.out.print("Syarat diskon: ");
        String syarat = scanner.nextLine();

        Diskon diskonBaru = new Diskon(nama, persentase, minimal, syarat);
        menuRestoran.tambahMenuItem(diskonBaru);
        System.out.println("Diskon berhasil ditambahkan!");
    }

    /**
     * Metode untuk mengubah menu
     */
    private static void ubahMenu() {
        if (menuRestoran.isMenuKosong()) {
            System.out.println("Belum ada menu yang tersedia.");
            return;
        }

        System.out.println("\n=== UBAH MENU ===");
        menuRestoran.tampilkanSemuaMenu();

        System.out.print("Pilih ID menu yang akan diubah: ");
        int id = getIntInput(1, Integer.MAX_VALUE);

        MenuItem item = menuRestoran.cariMenuById(id);
        if (item == null) {
            System.out.println("Menu dengan ID " + id + " tidak ditemukan.");
            return;
        }

        System.out.println("Menu yang dipilih: " + item);
        System.out.print("Nama baru: ");
        scanner.nextLine();
        String namaBaru = scanner.nextLine();

        System.out.print("Harga baru: ");
        double hargaBaru = getDoubleInput(0, Double.MAX_VALUE);

        System.out.println("Apakah Anda yakin ingin mengubah menu ini? (Ya/Tidak): ");
        String konfirmasi = scanner.nextLine();

        if (konfirmasi.equalsIgnoreCase("Ya")) {
            item.setNama(namaBaru);
            item.setHarga(hargaBaru);
            System.out.println("Menu berhasil diubah!");
        } else {
            System.out.println("Perubahan dibatalkan.");
        }
    }

    /**
     * Metode untuk menghapus menu
     */
    private static void hapusMenu() {
        if (menuRestoran.isMenuKosong()) {
            System.out.println("Belum ada menu yang tersedia.");
            return;
        }

        System.out.println("\n=== HAPUS MENU ===");
        menuRestoran.tampilkanSemuaMenu();

        System.out.print("Pilih ID menu yang akan dihapus: ");
        int id = getIntInput(1, Integer.MAX_VALUE);

        MenuItem item = menuRestoran.cariMenuById(id);
        if (item == null) {
            System.out.println("Menu dengan ID " + id + " tidak ditemukan.");
            return;
        }

        System.out.println("Menu yang akan dihapus: " + item);
        System.out.print("Apakah Anda yakin? (Ya/Tidak): ");
        String konfirmasi = scanner.nextLine();

        if (konfirmasi.equalsIgnoreCase("Ya")) {
            menuRestoran.hapusMenuItem(id);
        } else {
            System.out.println("Penghapusan dibatalkan.");
        }
    }

    /**
     * Metode untuk menampilkan detail menu
     */
    private static void tampilkanDetailMenu() {
        if (menuRestoran.isMenuKosong()) {
            System.out.println("Belum ada menu yang tersedia.");
            return;
        }

        System.out.println("\n=== DETAIL MENU ===");
        menuRestoran.tampilkanSemuaMenu();

        System.out.print("Pilih ID menu untuk detail: ");
        int id = getIntInput(1, Integer.MAX_VALUE);

        menuRestoran.tampilkanDetailMenu(id);
    }

    /**
     * Metode untuk mengurutkan menu
     */
    private static void urutkanMenu() {
        if (menuRestoran.isMenuKosong()) {
            System.out.println("Belum ada menu yang tersedia.");
            return;
        }

        System.out.println("\n=== URUTKAN MENU ===");
        System.out.println("1. Urutkan berdasarkan Harga (Termurah - Termahal)");
        System.out.println("2. Urutkan berdasarkan Nama (A-Z)");
        System.out.println("3. Kembali");
        System.out.print("Pilih: ");

        int pilihan = getIntInput(1, 3);

        switch (pilihan) {
            case 1:
                menuRestoran.urutkanMenuByHarga();
                System.out.println("Menu diurutkan berdasarkan harga!");
                menuRestoran.tampilkanSemuaMenu();
                break;
            case 2:
                menuRestoran.urutkanMenuByNama();
                System.out.println("Menu diurutkan berdasarkan nama!");
                menuRestoran.tampilkanSemuaMenu();
                break;
            case 3:
                return;
        }
    }

    /**
     * Metode untuk membuat pesanan baru
     */
    private static void buatPesananBaru() {
        if (menuRestoran.isMenuKosong()) {
            System.out.println("Belum ada menu yang tersedia.");
            return;
        }

        System.out.println("\n=== BUAT PESANAN BARU ===");

        scanner.nextLine();
        System.out.print("Nama Pelanggan: ");
        String namaPelanggan = scanner.nextLine();

        System.out.print("Nomor Meja: ");
        String noMeja = scanner.nextLine();

        pesananAktif = new Pesanan(namaPelanggan, noMeja);
        prosesPemesanan();
    }

    /**
     * Metode untuk memproses pemesanan
     */
    private static void prosesPemesanan() {
        if (pesananAktif == null) {
            System.out.println("Tidak ada pesanan aktif.");
            return;
        }

        while (true) {
            System.out.println("\n" + "-".repeat(50));
            System.out.println("                  PEMESANAN");
            System.out.println("-".repeat(50));
            System.out.println("1. Lihat Menu");
            System.out.println("2. Tambah Item ke Pesanan");
            System.out.println("3. Ubah Jumlah Item");
            System.out.println("4. Hapus Item dari Pesanan");
            System.out.println("5. Lihat Detail Pesanan");
            System.out.println("6. Selesaikan Pesanan dan Cetak Struk");
            System.out.println("7. Batal");
            System.out.print("Pilih (1-7): ");

            try {
                int pilihan = getIntInput(1, 7);

                switch (pilihan) {
                    case 1:
                        menuRestoran.tampilkanSemuaMenu();
                        break;
                    case 2:
                        tambahItemKePesanan();
                        break;
                    case 3:
                        ubahJumlahItem();
                        break;
                    case 4:
                        hapusItemDariPesanan();
                        break;
                    case 5:
                        pesananAktif.tampilDetailPesanan();
                        break;
                    case 6:
                        selesaikanPesanan();
                        return;
                    case 7:
                        pesananAktif = null;
                        return;
                }
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
                scanner.nextLine();
            }
        }
    }

    /**
     * Metode untuk menambah item ke pesanan
     */
    private static void tambahItemKePesanan() {
        System.out.println("\n=== TAMBAH ITEM KE PESANAN ===");
        menuRestoran.tampilkanSemuaMenu();

        System.out.print("Pilih ID menu: ");
        int id = getIntInput(1, Integer.MAX_VALUE);

        MenuItem item = menuRestoran.cariMenuById(id);
        if (item == null) {
            System.out.println("Menu tidak ditemukan.");
            return;
        }

        if (item.getKategori().equals("diskon")) {
            System.out.println("Diskon tidak dapat ditambahkan ke pesanan.");
            return;
        }

        System.out.print("Jumlah: ");
        int jumlah = getIntInput(1, 100);

        System.out.print("Catatan (opsional): ");
        scanner.nextLine();
        String catatan = scanner.nextLine();

        pesananAktif.tambahItem(item, jumlah, catatan);
    }

    /**
     * Metode untuk mengubah jumlah item
     */
    private static void ubahJumlahItem() {
        if (pesananAktif.isPesananKosong()) {
            System.out.println("Pesanan masih kosong.");
            return;
        }

        System.out.println("\n=== UBAH JUMLAH ITEM ===");
        pesananAktif.tampilDetailPesanan();

        System.out.print("Pilih ID menu yang akan diubah: ");
        int id = getIntInput(1, Integer.MAX_VALUE);

        System.out.print("Jumlah baru: ");
        int jumlahBaru = getIntInput(0, 100);

        pesananAktif.ubahJumlahItem(id, jumlahBaru);
    }

    /**
     * Metode untuk menghapus item dari pesanan
     */
    private static void hapusItemDariPesanan() {
        if (pesananAktif.isPesananKosong()) {
            System.out.println("Pesanan masih kosong.");
            return;
        }

        System.out.println("\n=== HAPUS ITEM PESANAN ===");
        pesananAktif.tampilDetailPesanan();

        System.out.print("Pilih ID menu yang akan dihapus: ");
        int id = getIntInput(1, Integer.MAX_VALUE);

        pesananAktif.hapusItem(id);
    }

    /**
     * Metode untuk menyelesaikan pesanan
     */
    private static void selesaikanPesanan() {
        if (pesananAktif.isPesananKosong()) {
            System.out.println("Pesanan masih kosong!");
            return;
        }

        System.out.println("\n=== SELESAIKAN PESANAN ===");

        // Ambil daftar diskon
        ArrayList<Diskon> daftarDiskon = menuRestoran.getDaftarDiskon();

        // Cetak struk dengan diskon
        pesananAktif.cetakStruk(daftarDiskon);

        // Simpan pesanan ke file
        try {
            FileHandler.simpanPesananKeFile(pesananAktif);
        } catch (Exception e) {
            System.err.println("Gagal menyimpan pesanan ke file: " + e.getMessage());
        }

        // Tambahkan ke daftar pesanan
        daftarPesanan.add(pesananAktif);

        System.out.println("\n1. Buat Pesanan Baru");
        System.out.println("2. Kembali ke Menu Utama");
        System.out.print("Pilih: ");

        int pilihan = getIntInput(1, 2);
        if (pilihan == 1) {
            buatPesananBaru();
        } else {
            pesananAktif = null;
        }
    }

    /**
     * Menu untuk mengelola pesanan
     */
    private static void menuKelolaPesanan() {
        if (daftarPesanan.isEmpty()) {
            System.out.println("Belum ada pesanan yang dibuat.");
            return;
        }

        while (true) {
            System.out.println("\n" + "-".repeat(50));
            System.out.println("              KELOLA PESANAN");
            System.out.println("-".repeat(50));
            System.out.println("Total Pesanan: " + daftarPesanan.size());
            System.out.println("1. Tampilkan Semua Pesanan");
            System.out.println("2. Lihat Detail Pesanan");
            System.out.println("3. Lihat Histori Pesanan dari File");
            System.out.println("4. Kembali");
            System.out.print("Pilih (1-4): ");

            try {
                int pilihan = getIntInput(1, 4);

                switch (pilihan) {
                    case 1:
                        tampilkanSemuaPesanan();
                        break;
                    case 2:
                        lihatDetailPesanan();
                        break;
                    case 3:
                        try {
                            FileHandler.tampilkanHistoriPesanan();
                        } catch (Exception e) {
                            System.err.println("Error membaca histori: " + e.getMessage());
                        }
                        break;
                    case 4:
                        return;
                }
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
                scanner.nextLine();
            }
        }
    }

    /**
     * Metode untuk menampilkan semua pesanan
     */
    private static void tampilkanSemuaPesanan() {
        System.out.println("\n=== DAFTAR SEMUA PESANAN ===");
        for (int i = 0; i < daftarPesanan.size(); i++) {
            Pesanan p = daftarPesanan.get(i);
            System.out.printf("%d. %s - %s (ID: #%d) - Total: Rp%.2f%n",
                i + 1, p.getNamaPelanggan(), p.getNoMeja(), p.getIdPesanan(), p.hitungTotalAkhir());
        }
    }

    /**
     * Metode untuk melihat detail pesanan
     */
    private static void lihatDetailPesanan() {
        System.out.println("\n=== DETAIL PESANAN ===");
        tampilkanSemuaPesanan();

        System.out.print("Pilih nomor pesanan: ");
        int nomor = getIntInput(1, daftarPesanan.size());

        Pesanan p = daftarPesanan.get(nomor - 1);
        p.tampilDetailPesanan();
    }

    /**
     * Menu manajemen file
     */
    private static void menuManajemenFile() {
        while (true) {
            System.out.println("\n" + "-".repeat(50));
            System.out.println("              MANAJEMEN FILE");
            System.out.println("-".repeat(50));
            System.out.println("1. Simpan Menu ke File");
            System.out.println("2. Muat Menu dari File");
            System.out.println("3. Backup Data Menu");
            System.out.println("4. Export Menu ke CSV");
            System.out.println("5. Lihat Histori Pesanan");
            System.out.println("6. Hapus Histori Pesanan");
            System.out.println("7. Statistik File");
            System.out.println("8. Kembali");
            System.out.print("Pilih (1-8): ");

            try {
                int pilihan = getIntInput(1, 8);

                switch (pilihan) {
                    case 1:
                        FileHandler.simpanMenuKeFile(menuRestoran);
                        break;
                    case 2:
                        menuRestoran = FileHandler.muatMenuDariFile();
                        System.out.println("Menu berhasil dimuat ulang!");
                        break;
                    case 3:
                        System.out.print("Nama file backup: ");
                        scanner.nextLine();
                        String backupFile = scanner.nextLine();
                        FileHandler.backupDataMenu(menuRestoran, backupFile);
                        break;
                    case 4:
                        System.out.print("Nama file CSV: ");
                        scanner.nextLine();
                        String csvFile = scanner.nextLine();
                        FileHandler.exportToCSV(menuRestoran, csvFile);
                        break;
                    case 5:
                        FileHandler.tampilkanHistoriPesanan();
                        break;
                    case 6:
                        FileHandler.hapusHistoriPesanan();
                        break;
                    case 7:
                        FileHandler.tampilkanStatistikFile();
                        break;
                    case 8:
                        return;
                }
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
                scanner.nextLine();
            }
        }
    }

    /**
     * Metode untuk menampilkan statistik
     */
    private static void tampilkanStatistik() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("                STATISTIK RESTORAN");
        System.out.println("=".repeat(60));

        menuRestoran.tampilkanStatistikMenu();

        System.out.println("\nStatistik Pesanan:");
        System.out.println("Total Pesanan: " + daftarPesanan.size());

        if (!daftarPesanan.isEmpty()) {
            double totalPendapatan = 0;
            for (Pesanan p : daftarPesanan) {
                totalPendapatan += p.hitungTotalAkhir();
            }
            System.out.println("Total Pendapatan: Rp" + String.format("%.2f", totalPendapatan));
            System.out.println("Rata-rata per Pesanan: Rp" + String.format("%.2f", totalPendapatan / daftarPesanan.size()));
        }

        System.out.println("=".repeat(60));
    }

    /**
     * Metode untuk keluar dari aplikasi
     */
    private static void keluarAplikasi() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("Terima kasih telah menggunakan Aplikasi Manajemen Restoran!");

        try {
            // Simpan data sebelum keluar
            FileHandler.simpanMenuKeFile(menuRestoran);
            System.out.println("Data berhasil disimpan.");
        } catch (Exception e) {
            System.err.println("Peringatan: Gagal menyimpan data - " + e.getMessage());
        }

        System.out.println("=".repeat(60));
    }

    /**
     * Metode untuk membuat menu default
     */
    private static void buatMenuDefault() {
        // Makanan
        menuRestoran.tambahMenuItem(new Makanan("Papeda Ikan Kuah Kuning", 38000, "pokok", "sedang"));
        menuRestoran.tambahMenuItem(new Makanan("Coto Makassar", 42000, "pokok", "sedang"));
        menuRestoran.tambahMenuItem(new Makanan("Ayam Taliwang", 45000, "pokok", "pedas"));
        menuRestoran.tambahMenuItem(new Makanan("Se'i Sapi", 50000, "pokok", "sedang"));
        menuRestoran.tambahMenuItem(new Makanan("Tinutuan (Bubur Manado)", 28000, "pokok", "tidak pedas"));
        menuRestoran.tambahMenuItem(new Makanan("Ikan Bakar Colo-Colo", 43000, "pokok", "pedas"));
        menuRestoran.tambahMenuItem(new Makanan("Konro Bakar", 52000, "pokok", "pedas"));
        menuRestoran.tambahMenuItem(new Makanan("Ayam Woku Belanga", 40000, "pokok", "pedas"));
        menuRestoran.tambahMenuItem(new Makanan("Jagung Bose", 20000, "pendamping", "tidak pedas"));
        menuRestoran.tambahMenuItem(new Makanan("Ikan Parende", 36000, "pokok", "sedang"));

        // Minuman
        menuRestoran.tambahMenuItem(new Minuman("Es Pisang Ijo", 20000, "dingin", "medium", true));
        menuRestoran.tambahMenuItem(new Minuman("Sarabba", 15000, "panas", "small", true));
        menuRestoran.tambahMenuItem(new Minuman("Air Guraka", 14000, "hangat", "small", true));
        menuRestoran.tambahMenuItem(new Minuman("Es Brenebon", 18000, "dingin", "medium", true));
        menuRestoran.tambahMenuItem(new Minuman("Kopi Rarobang", 16000, "panas", "medium", true));
        menuRestoran.tambahMenuItem(new Minuman("Es Palu Butung", 20000, "dingin", "medium", true));
        menuRestoran.tambahMenuItem(new Minuman("Jus Gandaria", 17000, "dingin", "large", true));
        menuRestoran.tambahMenuItem(new Minuman("Es Matoa", 19000, "dingin", "medium", true));
        menuRestoran.tambahMenuItem(new Minuman("Susu Kuda Liar", 25000, "dingin", "small", false));
        menuRestoran.tambahMenuItem(new Minuman("Es Markisa", 15000, "dingin", "medium", true));

        // Diskon
        menuRestoran.tambahMenuItem(new Diskon("Diskon Akhir Pekan", 0.15, 100000, "Berlaku Sabtu-Minggu"));
        menuRestoran.tambahMenuItem(new Diskon("Diskon Member", 0.10, 50000, "Untuk member restoran"));
    }

    /**
     * Metode helper untuk mendapatkan input integer
     */
    private static int getIntInput(int min, int max) {
        while (true) {
            try {
                int input = scanner.nextInt();
                scanner.nextLine(); // Clear buffer
                if (input >= min && input <= max) {
                    return input;
                } else {
                    System.out.print("Input harus antara " + min + " dan " + max + ": ");
                }
            } catch (InputMismatchException e) {
                System.out.print("Input harus berupa angka: ");
                scanner.nextLine(); // Clear invalid input
            }
        }
    }

    /**
     * Metode helper untuk mendapatkan input double
     */
    private static double getDoubleInput(double min, double max) {
        while (true) {
            try {
                double input = scanner.nextDouble();
                scanner.nextLine(); // Clear buffer
                if (input >= min && input <= max) {
                    return input;
                } else {
                    System.out.print("Input harus antara " + min + " dan " + max + ": ");
                }
            } catch (InputMismatchException e) {
                System.out.print("Input harus berupa angka: ");
                scanner.nextLine(); // Clear invalid input
            }
        }
    }
}
