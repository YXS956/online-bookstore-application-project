package bookStore.book.pojo;

public class Book {
    private Integer id ;
    private String bookImg ;
    private String bookName ;
    private Double price ;
    private Double eprice;
    private String author ;
    private Integer saleCount ; //销量
    private Integer bookCount ; //库存
    private Integer bookStatus = 0 ;//0:正常  -1：无效
    private String category;
    private String description;
    private String format;
    private String language;
    private String dimensions;
    private String editStat;
    private String publicDate;
    private String ISBN;
    private String publisher;
    private String publicCiCon;
    private Double paperdisPrice ;
    private Double edisPrice;

    public Book(){}

    public Book(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBookImg() {
        return bookImg;
    }

    public void setBookImg(String bookImg) {
        this.bookImg = bookImg;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getEprice() {
        return eprice;
    }

    public void setEprice(Double eprice) {
        this.eprice = eprice;
    }

    public Double getPaperdisPrice() {      return paperdisPrice;}

    public void setPaperdisPrice(Double paperdisPrice) {     this.paperdisPrice = paperdisPrice;}

    public Double getEdisPrice() {     return edisPrice;}

    public void setEdisPrice(Double edisPrice) {    this.edisPrice = edisPrice;}

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getSaleCount() {
        return saleCount;
    }

    public void setSaleCount(Integer saleCount) {
        this.saleCount = saleCount;
    }

    public Integer getBookCount() {
        return bookCount;
    }

    public void setBookCount(Integer bookCount) {
        this.bookCount = bookCount;
    }

    public Integer getBookStatus() {
        return bookStatus;
    }

    public void setBookStatus(Integer bookStatus) {
        this.bookStatus = bookStatus;
    }
    public String getDescription() {        return description;    }

    public void setDescription(String description) {        this.description = description;    }

    public String getCategory() {return category;}

    public void setCategory(String category) {this.category = category;}

    public String getFormat() {        return format;    }

    public void setFormat(String format) {        this.format = format;    }

    public String getLanguage() {        return language;    }

    public void setLanguage(String language) {        this.language = language;    }

    public String getDimensions() {        return dimensions;    }

    public void setDimensions(String dimensions) {        this.dimensions = dimensions;    }

    public String getEditStat() {        return editStat;    }

    public void setEditStat(String editStat) {        this.editStat = editStat;    }

    public String getPublicDate() {        return publicDate;    }

    public void setPublicDate(String publicDate) {        this.publicDate = publicDate;    }

    public String getISBN() {        return ISBN;    }

    public void setISBN(String ISBN) {        this.ISBN = ISBN;    }

    public String getPublisher() {        return publisher;    }

    public void setPublisher(String publisher) {        this.publisher = publisher;    }

    public String getPublicCiCon() {        return publicCiCon;    }

    public void setPublicCiCon(String publicCiCon) {        this.publicCiCon = publicCiCon;    }
}
