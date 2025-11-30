import java.io.*;
import java.util.ArrayList;

/**
 * Kelas FileHandler untuk menangani operasi I/O dan operasi file.
 * Mengimplementasikan konsep file processing dan exception handling.
 */
public class FileHandler {
    // Nama file default
    private static final String MENU_FILE = "menu_data.txt";
    private static final String PESANAN_FILE = "pesanan_data.txt";

    /**
     * Metode untuk menyimpan data menu ke file teks
     * @param menuRestoran MenuRestoran yang akan disimpan
     * @throws IOException jika terjadi error saat menulis file
     */
    public static void simpanMenuKeFile(MenuRestoran menuRestoran) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(MENU_FILE))) {
            // Tulis header
            writer.write("=== MENU RESTORAN " + menuRestoran.getNamaRestoran().toUpperCase() + " ===");
            writer.newLine();
            writer.write("Total Menu: " + menuRestoran.getJumlahMenu());
            writer.newLine();
            writer.write("=" .repeat(50));
            writer.newLine();
            writer.newLine();

            // Tulis data menu
            for (MenuItem item : menuRestoran.getDaftarMenu()) {
                writer.write(item.toFileString());
                writer.newLine();
            }

            System.out.println("Data menu berhasil disimpan ke file: " + MENU_FILE);
        } catch (IOException e) {
            throw new IOException("Gagal menyimpan data menu: " + e.getMessage());
        }
    }

    /**
     * Metode untuk memuat data menu dari file teks
     * @return MenuRestoran yang dimuat dari file
     * @throws IOException jika terjadi error saat membaca file
     */
    public static MenuRestoran muatMenuDariFile() throws IOException {
        MenuRestoran menuRestoran = new MenuRestoran("Restoran Saya");

        File file = new File(MENU_FILE);
        if (!file.exists()) {
            System.out.println("File menu tidak ditemukan. Membuat menu default...");
            buatMenuDefault(menuRestoran);
            return menuRestoran;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            boolean dataSection = false;

            while ((line = reader.readLine()) != null) {
                // Lewati header
                if (line.startsWith("=")) {
                    dataSection = true;
                    reader.readLine(); // Lewati baris kosong
                    continue;
                }

                if (dataSection && !line.trim().isEmpty()) {
                    MenuItem item = parseMenuItemFromFile(line);
                    if (item != null) {
                        menuRestoran.tambahMenuItem(item);
                    }
                }
            }

            System.out.println("Data menu berhasil dimuat dari file: " + MENU_FILE);
        } catch (IOException e) {
            throw new IOException("Gagal memuat data menu: " + e.getMessage());
        }

        return menuRestoran;
    }

    /**
     * Metode untuk menyimpan data pesanan ke file teks
     * @param pesanan Pesanan yang akan disimpan
     * @throws IOException jika terjadi error saat menulis file
     */
    public static void simpanPesananKeFile(Pesanan pesanan) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PESANAN_FILE, true))) { // Append mode
            writer.write("=".repeat(70));
            writer.newLine();
            writer.write("PESANAN #" + pesanan.getIdPesanan());
            writer.newLine();
            writer.write("Pelanggan: " + pesanan.getNamaPelanggan());
            writer.newLine();
            writer.write("Meja: " + pesanan.getNoMeja());
            writer.newLine();
            writer.write("Tanggal: " + new java.text.SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(pesanan.getTanggalPesanan()));
            writer.newLine();
            writer.write("-".repeat(70));
            writer.newLine();

            // Tulis item pesanan
            for (Pesanan.ItemPesanan item : pesanan.getItemPesanan()) {
                writer.write(String.format("%s|%d|%.2f|%s",
                    item.getMenuItem().getNama(),
                    item.getJumlah(),
                    item.getTotalHarga(),
                    item.getCatatan()));
                writer.newLine();
            }

            writer.write(String.format("TOTAL|%.2f", pesanan.hitungTotalAkhir()));
            writer.newLine();
            writer.newLine();

            System.out.println("Data pesanan berhasil disimpan ke file: " + PESANAN_FILE);
        } catch (IOException e) {
            throw new IOException("Gagal menyimpan data pesanan: " + e.getMessage());
        }
    }

    /**
     * Metode untuk menampilkan histori pesanan dari file
     * @throws IOException jika terjadi error saat membaca file
     */
    public static void tampilkanHistoriPesanan() throws IOException {
        File file = new File(PESANAN_FILE);
        if (!file.exists()) {
            System.out.println("Belum ada histori pesanan.");
            return;
        }

        System.out.println("=".repeat(70));
        System.out.println("                    HISTORI PESANAN");
        System.out.println("=".repeat(70));

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            throw new IOException("Gagal membaca histori pesanan: " + e.getMessage());
        }
    }

    /**
     * Metode untuk membackup data menu
     * @param menuRestoran MenuRestoran yang akan di-backup
     * @param backupFileName Nama file backup
     * @throws IOException jika terjadi error saat membuat backup
     */
    public static void backupDataMenu(MenuRestoran menuRestoran, String backupFileName) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(backupFileName))) {
            writer.write("# BACKUP MENU RESTORAN " + menuRestoran.getNamaRestoran().toUpperCase());
            writer.newLine();
            writer.write("# Tanggal: " + new java.text.SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new java.util.Date()));
            writer.newLine();
            writer.write("# Total Menu: " + menuRestoran.getJumlahMenu());
            writer.newLine();
            writer.newLine();

            for (MenuItem item : menuRestoran.getDaftarMenu()) {
                writer.write(item.toFileString());
                writer.newLine();
            }

            System.out.println("Backup berhasil dibuat: " + backupFileName);
        } catch (IOException e) {
            throw new IOException("Gagal membuat backup: " + e.getMessage());
        }
    }

    /**
     * Metode untuk menghapus file histori pesanan
     * @return true jika berhasil dihapus
     */
    public static boolean hapusHistoriPesanan() {
        File file = new File(PESANAN_FILE);
        if (file.exists() && file.delete()) {
            System.out.println("Histori pesanan berhasil dihapus.");
            return true;
        }
        System.out.println("Gagal menghapus histori pesanan atau file tidak ditemukan.");
        return false;
    }

    /**
     * Metode untuk mengecek apakah file ada
     * @param fileName Nama file yang akan dicek
     * @return true jika file ada
     */
    public static boolean fileAda(String fileName) {
        return new File(fileName).exists();
    }

    /**
     * Metode untuk mendapatkan ukuran file
     * @param fileName Nama file
     * @return Ukuran file dalam bytes
     */
    public static long getUkuranFile(String fileName) {
        File file = new File(fileName);
        return file.exists() ? file.length() : 0;
    }

    /**
     * Metode untuk parse MenuItem dari string file
     * @param line String yang akan diparse
     * @return MenuItem jika berhasil diparse, null jika gagal
     */
    private static MenuItem parseMenuItemFromFile(String line) {
        try {
            String[] parts = line.split("\\|");
            if (parts.length < 4) return null;

            String tipe = parts[0];
            String nama = parts[1];
            double harga = Double.parseDouble(parts[2]);
            String kategori = parts[3];

            switch (tipe) {
                case "Makanan":
                    if (parts.length >= 6) {
                        String jenisMakanan = parts[4];
                        String tingkatKepedasan = parts[5];
                        return new Makanan(nama, harga, jenisMakanan, tingkatKepedasan);
                    }
                    return new Makanan(nama, harga, "umum");

                case "Minuman":
                    if (parts.length >= 7) {
                        String jenisMinuman = parts[4];
                        String ukuran = parts[5];
                        boolean adaGula = Boolean.parseBoolean(parts[6]);
                        return new Minuman(nama, harga, jenisMinuman, ukuran, adaGula);
                    }
                    return new Minuman(nama, harga, "biasa");

                case "Diskon":
                    if (parts.length >= 7) {
                        double persentaseDiskon = Double.parseDouble(parts[4]);
                        double minimalPembelian = Double.parseDouble(parts[5]);
                        String syaratDiskon = parts[6];
                        Diskon diskon = new Diskon(nama, persentaseDiskon, minimalPembelian, syaratDiskon);
                        if (parts.length >= 8) {
                            diskon.setAktif(Boolean.parseBoolean(parts[7]));
                        }
                        return diskon;
                    }
                    break;

                default:
                    // Default ke MenuItem abstrak (tidak seharusnya terjadi)
                    break;
            }
        } catch (Exception e) {
            System.err.println("Error parsing line: " + line + " - " + e.getMessage());
        }
        return null;
    }

    /**
     * Metode untuk membuat menu default jika file tidak ada
     * @param menuRestoran MenuRestoran yang akan diisi
     */
    private static void buatMenuDefault(MenuRestoran menuRestoran) {
        // Makanan default
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

        // Minuman default
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

        // Diskon default
        menuRestoran.tambahMenuItem(new Diskon("Diskon Akhir Pekan", 0.15, 100000, "Berlaku Sabtu-Minggu"));
        menuRestoran.tambahMenuItem(new Diskon("Diskon Member", 0.10, 50000, "Untuk member restoran"));

        System.out.println("Menu default telah dibuat.");
    }

    /**
     * Metode untuk export data ke CSV
     * @param menuRestoran MenuRestoran yang akan diexport
     * @param csvFileName Nama file CSV
     * @throws IOException jika terjadi error
     */
    public static void exportToCSV(MenuRestoran menuRestoran, String csvFileName) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFileName))) {
            // Tulis header CSV
            writer.write("ID,Nama,Harga,Kategori,Jenis,Diskon");
            writer.newLine();

            // Tulis data
            for (MenuItem item : menuRestoran.getDaftarMenu()) {
                StringBuilder line = new StringBuilder();
                line.append(item.getId()).append(",");
                line.append("\"").append(item.getNama()).append("\",");
                line.append(item.getHarga()).append(",");
                line.append("\"").append(item.getKategori()).append("\",");

                if (item instanceof Makanan) {
                    Makanan makanan = (Makanan) item;
                    line.append("\"").append(makanan.getJenisMakanan()).append("\",");
                    line.append("\"").append(makanan.getTingkatKepedasan()).append("\"");
                } else if (item instanceof Minuman) {
                    Minuman minuman = (Minuman) item;
                    line.append("\"").append(minuman.getJenisMinuman()).append("\",");
                    line.append("\"").append(minuman.getUkuran()).append(" - ");
                    line.append(minuman.isAdaGula() ? "Gula" : "Tanpa Gula").append("\"");
                } else if (item instanceof Diskon) {
                    Diskon diskon = (Diskon) item;
                    line.append("\"").append(diskon.getPersentaseDiskon() * 100).append("% Off");
                    line.append(" (Min: Rp").append(diskon.getMinimalPembelian()).append(")\",");
                    line.append("\"").append(diskon.getSyaratDiskon()).append("\"");
                } else {
                    line.append("\"-\",\"-\"");
                }

                writer.write(line.toString());
                writer.newLine();
            }

            System.out.println("Data berhasil diexport ke CSV: " + csvFileName);
        } catch (IOException e) {
            throw new IOException("Gagal export ke CSV: " + e.getMessage());
        }
    }

    /**
     * Metode untuk mendapatkan statistik file
     */
    public static void tampilkanStatistikFile() {
        System.out.println("=".repeat(50));
        System.out.println("                STATISTIK FILE");
        System.out.println("=".repeat(50));

        System.out.println("File Menu: " + MENU_FILE);
        if (fileAda(MENU_FILE)) {
            System.out.println("  Status: Ada");
            System.out.println("  Ukuran: " + getUkuranFile(MENU_FILE) + " bytes");
        } else {
            System.out.println("  Status: Tidak Ada");
        }

        System.out.println("\nFile Pesanan: " + PESANAN_FILE);
        if (fileAda(PESANAN_FILE)) {
            System.out.println("  Status: Ada");
            System.out.println("  Ukuran: " + getUkuranFile(PESANAN_FILE) + " bytes");
        } else {
            System.out.println("  Status: Tidak Ada");
        }

        System.out.println("=".repeat(50));
    }
}
