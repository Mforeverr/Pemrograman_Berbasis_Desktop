import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/**
 * Kelas MenuRestoran untuk mengelola semua item menu dalam restoran.
 * Mengimplementasikan konsep encapsulation dan pengelolaan koleksi objek.
 */
public class MenuRestoran {
    // ArrayList untuk menyimpan semua item menu - implementasi encapsulation
    private ArrayList<MenuItem> daftarMenu;
    private String namaRestoran;

    /**
     * Constructor untuk kelas MenuRestoran
     * @param namaRestoran Nama restoran
     */
    public MenuRestoran(String namaRestoran) {
        this.namaRestoran = namaRestoran;
        this.daftarMenu = new ArrayList<>();
    }

    // Getter untuk daftar menu (protected untuk encapsulation)
    public ArrayList<MenuItem> getDaftarMenu() {
        return new ArrayList<>(daftarMenu); // Return copy untuk encapsulation
    }

    public String getNamaRestoran() {
        return namaRestoran;
    }

    public void setNamaRestoran(String namaRestoran) {
        this.namaRestoran = namaRestoran;
    }

    /**
     * Metode untuk menambahkan menu item ke dalam daftar
     * @param item MenuItem yang akan ditambahkan
     */
    public void tambahMenuItem(MenuItem item) {
        if (item != null) {
            daftarMenu.add(item);
            System.out.println("Menu berhasil ditambahkan: " + item.getNama());
        } else {
            throw new IllegalArgumentException("Menu item tidak boleh null");
        }
    }

    /**
     * Metode untuk menghapus menu item berdasarkan ID
     * @param id ID menu item yang akan dihapus
     * @return true jika berhasil dihapus, false jika tidak ditemukan
     */
    public boolean hapusMenuItem(int id) {
        Iterator<MenuItem> iterator = daftarMenu.iterator();
        while (iterator.hasNext()) {
            MenuItem item = iterator.next();
            if (item.getId() == id) {
                iterator.remove();
                System.out.println("Menu berhasil dihapus: " + item.getNama());
                return true;
            }
        }
        System.out.println("Menu dengan ID " + id + " tidak ditemukan");
        return false;
    }

    /**
     * Metode untuk mencari menu item berdasarkan ID
     * @param id ID menu item yang dicari
     * @return MenuItem jika ditemukan, null jika tidak
     */
    public MenuItem cariMenuById(int id) {
        for (MenuItem item : daftarMenu) {
            if (item.getId() == id) {
                return item;
            }
        }
        return null;
    }

    /**
     * Metode untuk mencari menu item berdasarkan nama
     * @param nama Nama menu item yang dicari
     * @return MenuItem jika ditemukan, null jika tidak
     */
    public MenuItem cariMenuByNama(String nama) {
        for (MenuItem item : daftarMenu) {
            if (item.getNama().equalsIgnoreCase(nama)) {
                return item;
            }
        }
        return null;
    }

    /**
     * Metode untuk mendapatkan menu item berdasarkan kategori
     * @param kategori Kategori yang dicari
     * @return List MenuItem dengan kategori tertentu
     */
    public ArrayList<MenuItem> getMenuByKategori(String kategori) {
        ArrayList<MenuItem> hasil = new ArrayList<>();
        for (MenuItem item : daftarMenu) {
            if (item.getKategori().equalsIgnoreCase(kategori)) {
                hasil.add(item);
            }
        }
        return hasil;
    }

    /**
     * Metode untuk menampilkan semua menu yang tersedia
     */
    public void tampilkanSemuaMenu() {
        System.out.println("=== MENU " + namaRestoran.toUpperCase() + " ===");
        if (daftarMenu.isEmpty()) {
            System.out.println("Belum ada menu yang tersedia.");
            return;
        }

        // Kelompokkan berdasarkan kategori
        tampilkanMenuByKategori("makanan");
        tampilkanMenuByKategori("minuman");
        tampilkanMenuByKategori("diskon");
    }

    /**
     * Metode untuk menampilkan menu berdasarkan kategori tertentu
     * @param kategori Kategori yang akan ditampilkan
     */
    public void tampilkanMenuByKategori(String kategori) {
        ArrayList<MenuItem> menuKategori = getMenuByKategori(kategori);
        if (!menuKategori.isEmpty()) {
            System.out.println("\n--- " + kategori.toUpperCase() + " ---");
            for (MenuItem item : menuKategori) {
                System.out.println(item);
            }
        }
    }

    /**
     * Metode untuk menampilkan detail menu dengan polymorphism
     * @param id ID menu yang akan ditampilkan detailnya
     */
    public void tampilkanDetailMenu(int id) {
        MenuItem item = cariMenuById(id);
        if (item != null) {
            item.tampilMenu(); // Polymorphism method call
        } else {
            System.out.println("Menu dengan ID " + id + " tidak ditemukan");
        }
    }

    /**
     * Metode untuk mengurutkan menu berdasarkan harga (ascending)
     */
    public void urutkanMenuByHarga() {
        Collections.sort(daftarMenu, new Comparator<MenuItem>() {
            @Override
            public int compare(MenuItem m1, MenuItem m2) {
                return Double.compare(m1.getHarga(), m2.getHarga());
            }
        });
    }

    /**
     * Metode untuk mengurutkan menu berdasarkan nama (alphabetical)
     */
    public void urutkanMenuByNama() {
        Collections.sort(daftarMenu, new Comparator<MenuItem>() {
            @Override
            public int compare(MenuItem m1, MenuItem m2) {
                return m1.getNama().compareToIgnoreCase(m2.getNama());
            }
        });
    }

    /**
     * Metode untuk menghitung total harga semua menu
     * @return Total harga semua menu
     */
    public double getTotalHargaSemuaMenu() {
        double total = 0;
        for (MenuItem item : daftarMenu) {
            if (!item.getKategori().equals("diskon")) {
                total += item.getHarga();
            }
        }
        return total;
    }

    /**
     * Metode untuk mendapatkan statistik menu
     */
    public void tampilkanStatistikMenu() {
        int totalMakanan = getMenuByKategori("makanan").size();
        int totalMinuman = getMenuByKategori("minuman").size();
        int totalDiskon = getMenuByKategori("diskon").size();

        System.out.println("=== STATISTIK MENU ===");
        System.out.println("Total Menu: " + daftarMenu.size());
        System.out.println("- Makanan: " + totalMakanan);
        System.out.println("- Minuman: " + totalMinuman);
        System.out.println("- Diskon: " + totalDiskon);
        System.out.println("Total Nilai Menu: Rp" + String.format("%.2f", getTotalHargaSemuaMenu()));
        System.out.println("====================");
    }

    /**
     * Metode untuk membersihkan semua menu
     */
    public void clearMenu() {
        daftarMenu.clear();
        System.out.println("Semua menu telah dihapus.");
    }

    /**
     * Metode untuk mendapatkan jumlah menu
     * @return Jumlah total menu
     */
    public int getJumlahMenu() {
        return daftarMenu.size();
    }

    /**
     * Metode untuk mengecek apakah menu kosong
     * @return true jika menu kosong
     */
    public boolean isMenuKosong() {
        return daftarMenu.isEmpty();
    }

    /**
     * Metode untuk mendapatkan menu makanan saja
     * @return List menu makanan
     */
    public ArrayList<Makanan> getDaftarMakanan() {
        ArrayList<Makanan> makananList = new ArrayList<>();
        for (MenuItem item : daftarMenu) {
            if (item instanceof Makanan) {
                makananList.add((Makanan) item);
            }
        }
        return makananList;
    }

    /**
     * Metode untuk mendapatkan menu minuman saja
     * @return List menu minuman
     */
    public ArrayList<Minuman> getDaftarMinuman() {
        ArrayList<Minuman> minumanList = new ArrayList<>();
        for (MenuItem item : daftarMenu) {
            if (item instanceof Minuman) {
                minumanList.add((Minuman) item);
            }
        }
        return minumanList;
    }

    /**
     * Metode untuk mendapatkan menu diskon saja
     * @return List menu diskon
     */
    public ArrayList<Diskon> getDaftarDiskon() {
        ArrayList<Diskon> diskonList = new ArrayList<>();
        for (MenuItem item : daftarMenu) {
            if (item instanceof Diskon) {
                diskonList.add((Diskon) item);
            }
        }
        return diskonList;
    }

    /**
     * Override metode toString untuk representasi objek
     */
    @Override
    public String toString() {
        return String.format("MenuRestoran[nama=%s, totalMenu=%d]",
            namaRestoran, daftarMenu.size());
    }
}