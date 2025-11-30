import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Main class untuk aplikasi restoran sederhana
 */
public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static ArrayList<Menu> daftarMenu = new ArrayList<>();
    private static ArrayList<Pesanan> daftarPesanan = new ArrayList<>();
    private static int nextMenuId = 1;

    // Konstanta untuk biaya dan pajak
    private static final double PAJAK = 0.10; // 10%
    private static final double BIAYA_PELAYANAN = 20000.0;
    private static final double BATAS_DISKON = 100000.0;
    private static final double DISKON = 0.10; // 10%
    private static final double BATAS_BOGOF = 50000.0;

    /**
     * Inner class untuk menyimpan data pesanan
     */
    static class Pesanan {
        Menu menu;
        int jumlah;
        double hargaSatuan;

        public Pesanan(Menu menu, int jumlah) {
            this.menu = menu;
            this.jumlah = jumlah;
            this.hargaSatuan = menu.getHarga();
        }

        public double getTotal() {
            return jumlah * hargaSatuan;
        }

        @Override
        public String toString() {
            return String.format("%s - %d x Rp%.2f = Rp%.2f",
                menu.getNama(), jumlah, hargaSatuan, getTotal());
        }
    }

    public static void main(String[] args) {
        inisialisasiMenu();
        mainMenu();
    }

    /**
     * Menginisialisasi menu default restoran
     */
    private static void inisialisasiMenu() {
        // Menu Makanan
        daftarMenu.add(new Menu(nextMenuId++, "Papeda Ikan Kuah Kuning", 38000, "makanan"));
        daftarMenu.add(new Menu(nextMenuId++, "Coto Makassar", 42000, "makanan"));
        daftarMenu.add(new Menu(nextMenuId++, "Ayam Taliwang", 45000, "makanan"));
        daftarMenu.add(new Menu(nextMenuId++, "Se'i Sapi", 48000, "makanan"));
        daftarMenu.add(new Menu(nextMenuId++, "Tinutuan (Bubur Manado)", 30000, "makanan"));
        daftarMenu.add(new Menu(nextMenuId++, "Ikan Bakar Colo-Colo", 40000, "makanan"));
        daftarMenu.add(new Menu(nextMenuId++, "Konro Bakar", 47000, "makanan"));
        daftarMenu.add(new Menu(nextMenuId++, "Ayam Woku Belanga", 43000, "makanan"));
        daftarMenu.add(new Menu(nextMenuId++, "Jagung Bose", 25000, "makanan"));
        daftarMenu.add(new Menu(nextMenuId++, "Ikan Parende", 39000, "makanan"));

        // Menu Minuman
        daftarMenu.add(new Menu(nextMenuId++, "Es Pisang Ijo", 18000, "minuman"));
        daftarMenu.add(new Menu(nextMenuId++, "Sarabba", 15000, "minuman"));
        daftarMenu.add(new Menu(nextMenuId++, "Air Guraka", 12000, "minuman"));
        daftarMenu.add(new Menu(nextMenuId++, "Es Brenebon", 16000, "minuman"));
        daftarMenu.add(new Menu(nextMenuId++, "Kopi Rarobang", 20000, "minuman"));
        daftarMenu.add(new Menu(nextMenuId++, "Es Palu Butung", 17000, "minuman"));
        daftarMenu.add(new Menu(nextMenuId++, "Jus Gandaria", 19000, "minuman"));
        daftarMenu.add(new Menu(nextMenuId++, "Es Matoa", 17000, "minuman"));
        daftarMenu.add(new Menu(nextMenuId++, "Susu Kuda Liar", 25000, "minuman"));
        daftarMenu.add(new Menu(nextMenuId++, "Es Markisa", 15000, "minuman"));
    }

    /**
     * Menu utama aplikasi
     */
    private static void mainMenu() {
        while (true) {
            System.out.println("\n=== APLIKASI RESTORAN MAKAAN DAN MINUMAN ===");
            System.out.println("1. Pesan Menu (Pelanggan)");
            System.out.println("2. Kelola Menu (Pemilik)");
            System.out.println("3. Keluar");
            System.out.print("Pilih menu: ");

            int pilihan = getIntInput(1, 3);

            switch (pilihan) {
                case 1:
                    menuPelanggan();
                    break;
                case 2:
                    menuPemilik();
                    break;
                case 3:
                    System.out.println("Terima kasih telah menggunakan aplikasi restoran!");
                    System.exit(0);
                    break;
            }
        }
    }

    /**
     * Menu untuk pelanggan
     */
    private static void menuPelanggan() {
        daftarPesanan.clear();
        System.out.println("\n=== MENU PELANGGAN ===");

        while (true) {
            System.out.println("\n1. Lihat Daftar Menu");
            System.out.println("2. Pesan Menu");
            System.out.println("3. Selesai Pemesanan");
            System.out.println("4. Kembali ke Menu Utama");
            System.out.print("Pilih: ");

            int pilihan = getIntInput(1, 4);

            switch (pilihan) {
                case 1:
                    tampilkanDaftarMenu();
                    break;
                case 2:
                    prosesPemesanan();
                    break;
                case 3:
                    if (daftarPesanan.isEmpty()) {
                        System.out.println("Belum ada pesanan. Silakan pesan terlebih dahulu.");
                    } else {
                        prosesPembayaran();
                        return;
                    }
                    break;
                case 4:
                    return;
            }
        }
    }

    /**
     * Menampilkan daftar menu yang dikelompokkan berdasarkan kategori
     */
    private static void tampilkanDaftarMenu() {
        System.out.println("\n=== DAFTAR MENU RESTORAN ===");

        // Tampilkan makanan
        System.out.println("\n--- MAKANAN ---");
        for (Menu menu : daftarMenu) {
            if (menu.getKategori().equals("makanan")) {
                System.out.println(menu);
            }
        }

        // Tampilkan minuman
        System.out.println("\n--- MINUMAN ---");
        for (Menu menu : daftarMenu) {
            if (menu.getKategori().equals("minuman")) {
                System.out.println(menu);
            }
        }
    }

    /**
     * Memproses pemesanan dari pelanggan
     */
    private static void prosesPemesanan() {
        if (daftarMenu.isEmpty()) {
            System.out.println("Belum ada menu yang tersedia.");
            return;
        }

        tampilkanDaftarMenu();

        while (true) {
            System.out.print("\nMasukkan nomor menu (0 untuk selesai): ");
            int nomorMenu = getIntInput(0, daftarMenu.size());

            if (nomorMenu == 0) {
                if (!daftarPesanan.isEmpty()) {
                    System.out.println("Pemesanan selesai.");
                }
                break;
            }

            Menu menuDipilih = cariMenuById(nomorMenu);
            if (menuDipilih != null) {
                System.out.print("Masukkan jumlah: ");
                int jumlah = getIntInput(1, 100);

                // Cek apakah menu sudah ada di pesanan
                boolean pesananAda = false;
                for (Pesanan pesanan : daftarPesanan) {
                    if (pesanan.menu.getId() == nomorMenu) {
                        pesanan.jumlah += jumlah;
                        pesananAda = true;
                        break;
                    }
                }

                if (!pesananAda) {
                    daftarPesanan.add(new Pesanan(menuDipilih, jumlah));
                }

                System.out.println("Berhasil menambahkan " + menuDipilih.getNama() + " (" + jumlah + "x)");

                // Tampilkan pesanan sementara
                System.out.println("\nPesanan Anda saat ini:");
                for (Pesanan pesanan : daftarPesanan) {
                    System.out.println("- " + pesanan);
                }
            }
        }
    }

    /**
     * Memproses pembayaran dan mencetak struk
     */
    private static void prosesPembayaran() {
        double subtotal = 0;
        System.out.println("\n=== DETAIL PEMBAYARAN ===");
        System.out.println("Pesanan Anda:");

        // Tampilkan detail pesanan
        for (Pesanan pesanan : daftarPesanan) {
            System.out.println("- " + pesanan);
            subtotal += pesanan.getTotal();
        }

        // Hitung pajak
        double pajak = subtotal * PAJAK;

        // Hitung total sebelum diskon
        double totalSebelumDiskon = subtotal + pajak + BIAYA_PELAYANAN;

        // Cek diskon
        double diskonHarga = 0;
        String infoDiskon = "";

        if (totalSebelumDiskon > BATAS_DISKON) {
            diskonHarga = totalSebelumDiskon * DISKON;
            infoDiskon = String.format("Diskon 10%%: -Rp%.2f", diskonHarga);
        }

        // Cek penawaran BOGO untuk minuman
        String infoBogo = "";
        if (totalSebelumDiskon > BATAS_BOGOF) {
            // Cari minuman termurah untuk BOGO
            Menu minumanTermurah = null;
            for (Pesanan pesanan : daftarPesanan) {
                if (pesanan.menu.getKategori().equals("minuman")) {
                    if (minumanTermurah == null || pesanan.menu.getHarga() < minumanTermurah.getHarga()) {
                        minumanTermurah = pesanan.menu;
                    }
                }
            }

            if (minumanTermurah != null) {
                infoBogo = String.format("Bonus: %s (Beli 1 Gratis 1)", minumanTermurah.getNama());
            }
        }

        // Hitung total akhir
        double totalAkhir = totalSebelumDiskon - diskonHarga;

        // Cetak struk lengkap
        System.out.println("\n" + "=".repeat(50));
        System.out.println("                STRUK PEMBAYARAN");
        System.out.println("=".repeat(50));
        System.out.println("Pesanan:");
        for (Pesanan pesanan : daftarPesanan) {
            System.out.printf("  %-25s %2d x %8.2f = %8.2f%n",
                pesanan.menu.getNama(), pesanan.jumlah, pesanan.hargaSatuan, pesanan.getTotal());
        }

        System.out.println("-".repeat(50));
        System.out.printf("%-35s %8.2f%n", "Subtotal:", subtotal);
        System.out.printf("%-35s %8.2f%n", "Pajak (10%):", pajak);
        System.out.printf("%-35s %8.2f%n", "Biaya Pelayanan:", BIAYA_PELAYANAN);

        if (!infoDiskon.isEmpty()) {
            System.out.printf("%-35s %8.2f%n", "Diskon 10%:", -diskonHarga);
        }

        System.out.println("=".repeat(50));
        System.out.printf("%-35s %8.2f%n", "TOTAL PEMBAYARAN:", totalAkhir);

        if (!infoBogo.isEmpty()) {
            System.out.println("\nPenawaran Spesial:");
            System.out.println("  " + infoBogo);
        }
        System.out.println("=".repeat(50));

        System.out.print("\nTekan Enter untuk melanjutkan...");
        scanner.nextLine();

        // Reset pesanan
        daftarPesanan.clear();
    }

    /**
     * Menu untuk pemilik restoran
     */
    private static void menuPemilik() {
        while (true) {
            System.out.println("\n=== MENU PEMILIK ===");
            System.out.println("1. Tambah Menu Baru");
            System.out.println("2. Ubah Harga Menu");
            System.out.println("3. Hapus Menu");
            System.out.println("4. Lihat Semua Menu");
            System.out.println("5. Kembali ke Menu Utama");
            System.out.print("Pilih: ");

            int pilihan = getIntInput(1, 5);

            switch (pilihan) {
                case 1:
                    tambahMenu();
                    break;
                case 2:
                    ubahHargaMenu();
                    break;
                case 3:
                    hapusMenu();
                    break;
                case 4:
                    tampilkanDaftarMenu();
                    break;
                case 5:
                    return;
            }
        }
    }

    /**
     * Menambah menu baru
     */
    private static void tambahMenu() {
        System.out.println("\n=== TAMBAH MENU BARU ===");

        scanner.nextLine(); // Clear buffer

        System.out.print("Nama menu: ");
        String nama = scanner.nextLine();

        System.out.print("Kategori (makanan/minuman): ");
        String kategori;
        do {
            kategori = scanner.nextLine().toLowerCase();
            if (!kategori.equals("makanan") && !kategori.equals("minuman")) {
                System.out.print("Kategori harus 'makanan' atau 'minuman': ");
            }
        } while (!kategori.equals("makanan") && !kategori.equals("minuman"));

        System.out.print("Harga: ");
        double harga = getDoubleInput(0, Double.MAX_VALUE);

        daftarMenu.add(new Menu(nextMenuId++, nama, harga, kategori));
        System.out.println("Menu berhasil ditambahkan!");
    }

    /**
     * Mengubah harga menu
     */
    private static void ubahHargaMenu() {
        if (daftarMenu.isEmpty()) {
            System.out.println("Belum ada menu yang tersedia.");
            return;
        }

        System.out.println("\n=== UBAH HARGA MENU ===");
        tampilkanDaftarMenu();

        System.out.print("Pilih nomor menu yang akan diubah: ");
        int nomorMenu = getIntInput(1, daftarMenu.size());

        Menu menuDipilih = cariMenuById(nomorMenu);
        if (menuDipilih != null) {
            System.out.println("Menu yang dipilih: " + menuDipilih);
            System.out.print("Harga baru: ");
            double hargaBaru = getDoubleInput(0, Double.MAX_VALUE);

            System.out.printf("Anda yakin ingin mengubah harga '%s' dari Rp%.2f menjadi Rp%.2f? (Ya/Tidak): ",
                menuDipilih.getNama(), menuDipilih.getHarga(), hargaBaru);

            String konfirmasi = scanner.nextLine().toLowerCase();
            if (konfirmasi.equals("ya")) {
                menuDipilih.setHarga(hargaBaru);
                System.out.println("Harga menu berhasil diubah!");
            } else {
                System.out.println("Perubahan dibatalkan.");
            }
        }
    }

    /**
     * Menghapus menu
     */
    private static void hapusMenu() {
        if (daftarMenu.isEmpty()) {
            System.out.println("Belum ada menu yang tersedia.");
            return;
        }

        System.out.println("\n=== HAPUS MENU ===");
        tampilkanDaftarMenu();

        System.out.print("Pilih nomor menu yang akan dihapus: ");
        int nomorMenu = getIntInput(1, daftarMenu.size());

        Menu menuDipilih = cariMenuById(nomorMenu);
        if (menuDipilih != null) {
            System.out.println("Menu yang dipilih: " + menuDipilih);
            System.out.printf("Anda yakin ingin menghapus menu '%s'? (Ya/Tidak): ", menuDipilih.getNama());

            String konfirmasi = scanner.nextLine().toLowerCase();
            if (konfirmasi.equals("ya")) {
                daftarMenu.remove(menuDipilih);
                System.out.println("Menu berhasil dihapus!");
            } else {
                System.out.println("Penghapusan dibatalkan.");
            }
        }
    }

    /**
     * Mencari menu berdasarkan ID
     */
    private static Menu cariMenuById(int id) {
        for (Menu menu : daftarMenu) {
            if (menu.getId() == id) {
                return menu;
            }
        }
        return null;
    }

    /**
     * Mendapatkan input integer dengan validasi
     */
    private static int getIntInput(int min, int max) {
        while (true) {
            try {
                int input = scanner.nextInt();
                scanner.nextLine(); // Clear buffer

                if (input >= min && input <= max) {
                    return input;
                } else {
                    System.out.printf("Input harus antara %d dan %d: ", min, max);
                }
            } catch (InputMismatchException e) {
                System.out.print("Input harus berupa angka: ");
                scanner.nextLine(); // Clear invalid input
            }
        }
    }

    /**
     * Mendapatkan input double dengan validasi
     */
    private static double getDoubleInput(double min, double max) {
        while (true) {
            try {
                double input = scanner.nextDouble();
                scanner.nextLine(); // Clear buffer

                if (input >= min && input <= max) {
                    return input;
                } else {
                    System.out.printf("Input harus antara %.2f dan %.2f: ", min, max);
                }
            } catch (InputMismatchException e) {
                System.out.print("Input harus berupa angka: ");
                scanner.nextLine(); // Clear invalid input
            }
        }
    }
}
