package cinema;

import java.util.LinkedList;
import java.util.List;
import java.io.FileOutputStream;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Cart {

    private List<Food> food = new LinkedList<>();
    private List<Integer> occ = new LinkedList<>();
    private Film film;
    private int child, adult;
    private double total = 0;
    private final FileChooser fileChooser = new FileChooser();

    public void addFood(Food food) {
        int pos = this.food.indexOf(food);
        if (pos == -1) {
            this.food.add(food);
            this.occ.add(1);
        } else {
            this.occ.set(pos, this.occ.get(pos) + 1);
        }
        //System.out.println(bill());
    }

    public void setFilm(Film film) {
        this.film = film;
    }

    public void setChild(int child) {
        this.child = child;
    }

    public List<Food> getFood() {
        return food;
    }

    public List<Integer> getOcc() {
        return occ;
    }

    public Film getFilm() {
        return film;
    }

    public int getChild() {
        return child;
    }

    public int getAdult() {
        return adult;
    }

    public double getTotal() {
        return total;
    }

    public void setAdult(int adult) {
        this.adult = adult;
    }

    public void flushFood() {
        this.food.clear();
        this.occ.clear();
    }

    public String toString() {
        String s = this.film.getIme() + "\nChild tickets: " + child + "\nAdult tickets: " + adult + "\n";
        for (int i = 0; i < food.size(); i++) {
            s += food.get(i).getIme() + " x" + occ.get(i) + "\n";
        }
        return s;
    }

    public void generatePDF(Stage fourthStage, Stage thirdStage, Stage secondaryStage, Stage primaryStage, List<String> selectedSeats, int sala) {
        double total;
        try {
            Document document = new Document();
            configureFileChooser(fileChooser);
            File file = fileChooser.showSaveDialog(primaryStage);
            if (file != null) {
                String file_name = file.getPath();
                PdfWriter.getInstance(document, new FileOutputStream(file_name));
            }

            document.open();

            String p0 = "=================================\n                   CINEMAGIC D.O.O.\n                      Mijatovih njiva 6\n                            Belgrade\n=================================\n";
            String p1 = p0 + this.film.getIme() + "\n----------------------------------------------------------\nITEMS & PRICE\n\n";
            if (child != 0) {
                p1 += "Child tickets \nx" + child + "          " + String.format("%.2f", child * film.getCenaKarteD()) + "€\n";
            }
            if (adult != 0) {
                p1 += "Adult tickets \nx" + adult + "          " + String.format("%.2f", adult * film.getCenaKarteO()) + "€\n\n";
            }
            if (adult == 0) {
                p1 += "\n";
            }
            total = (child * film.getCenaKarteD() + adult * film.getCenaKarteO());

            for (int i = 0; i < food.size(); i++) {
                p1 += food.get(i).getIme() + " \nx" + occ.get(i) + "          " + String.format("%.2f", occ.get(i) * food.get(i).getCena()) + "€\n";
                total += occ.get(i) * food.get(i).getCena();
            }
            p1 += "\n----------------------------------------------------------\nTOTAL:             " + String.format("%.2f", total) + "€\n=================================\n";
            p1 += "Room: " + sala + "\nSeats: " + selectedSeats.get(0);
            for (int i = 1; i < selectedSeats.size(); i++) {
                p1 += ", " + selectedSeats.get(i);
            }
            p1 += ".\n";

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd '\n'HH:mm:ss ");
            Date date = new Date(System.currentTimeMillis());
            p1 += formatter.format(date) + "\nThank you for using our application!\nWe hope you will have a great time in our cinema!\n=================================\n";

            Paragraph para1 = new Paragraph(p1);

            document.add(para1);
            document.add(Image.getInstance("C:\\Users\\Lazar\\Documents\\NetBeansProjects\\Cinema\\src\\cinema\\Images\\logo1.png"));

            document.close();
            primaryStage.close();
            secondaryStage.close();
            thirdStage.close();
            fourthStage.close();

        } catch (Exception ex) {
            System.out.println(ex);
        }

    }

    private static void configureFileChooser(
            final FileChooser fileChooser) {
        fileChooser.setTitle("Download your ticket");
        fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home"))
        );
        fileChooser.setInitialFileName("MyTickets");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PDF files", "*.pdf")
        );
    }

}
