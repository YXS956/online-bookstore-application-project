package bookStore.book.dao.impl;

import bookStore.mybatis.basedao.BaseDAO;
import bookStore.book.dao.BookDAO;
import bookStore.book.pojo.Book;

import java.util.List;

public class BookDAOImpl extends BaseDAO<Book> implements BookDAO {
    @Override
    public List<Book> getBookList(Integer pageNo) {
        return executeQuery("select id,bookImg,bookName,price,eprice,author,saleCount,bookCount,bookStatus,description,category,format,`language`,dimensions,editStat,publicDate,ISBN,publisher,publicCiCon,paperdisPrice,edisPrice " +
                "from t_book b left join t_discount_price p on b.id = p.bookid where bookStatus=0 LIMIT ?,5",(pageNo-1)*5);
    }


    @Override
    public Integer getTotalBookNo() {
        Object num = super.executeComplexQuery("select count(*) from t_book where bookStatus=0")[0];
        Integer num2 =  Integer.parseInt(num.toString());
        return num2;
    }

    @Override
    public Book getRandomBook(Integer index) {
        return executeQuery("SELECT\tid,bookImg,bookName,price,eprice,author,saleCount,bookCount,bookStatus,description,category,format,`language`,dimensions,editStat,publicDate,ISBN,publisher,publicCiCon,paperdisPrice,edisPrice FROM \n" +
                "\t(\tSELECT ROW_NUMBER() OVER () row_num,id,bookImg,bookName,price,eprice,author,saleCount,bookCount,bookStatus,description,category,format,`language`,\n" +
                "\t\tdimensions,editStat,publicDate,ISBN,publisher,publicCiCon,paperdisPrice,edisPrice FROM t_book b\tLEFT JOIN t_discount_price p ON b.id = p.bookid \n" +
                "\tWHERE\tbookStatus = 0 ) AS a WHERE\ta.row_num = ?;",index).get(0);
    }

    @Override
    public List<Book> getAllBook() {
        return executeQuery("select id,bookImg,bookName,price,eprice,author,saleCount,bookCount,bookStatus,description,category,format,`language`,dimensions,editStat,publicDate,ISBN,publisher,publicCiCon,paperdisPrice,edisPrice " +
                "from t_book b left join t_discount_price p on b.id = p.bookid");
    }

    @Override
    public List<Book> getBookListByKey(String keyWord, Integer pageNo) {
        return executeQuery("select id,bookImg,bookName,price,eprice,author,saleCount,bookCount,bookStatus,description,category,format,`language`,dimensions,editStat,publicDate,ISBN,publisher,publicCiCon,paperdisPrice,edisPrice " +
                "from t_book b left join t_discount_price p on b.id = p.bookid where bookStatus=0 and (bookname LIKE ? or author LIKE ? or category Like ? or language Like ?) LIMIT ?,5","%"+keyWord+"%","%"+keyWord+"%","%"+keyWord+"%","%"+keyWord+"%",(pageNo-1)*5);
    }

    @Override
    public List<Book> getBookListByPrice(Double minPrice, Double maxPrice, Integer pageNo) {
        return executeQuery("select id,bookImg,bookName,price,eprice,author,saleCount,bookCount,bookStatus,description,category,format,`language`,dimensions,editStat,publicDate,ISBN,publisher,publicCiCon,paperdisPrice,edisPrice " +
                "from t_book b left join t_discount_price p on b.id = p.bookid where bookStatus=0 and price between ? and ? LIMIT ?,5",minPrice,maxPrice,(pageNo-1)*5);
    }

    @Override
    public List<Book> getBookListByKeyPrice(String keyWord,Double minPrice, Double maxPrice, Integer pageNo) {
        return executeQuery("select id,bookImg,bookName,price,eprice,author,saleCount,bookCount,bookStatus,description,category,format,`language`,dimensions,editStat,publicDate,ISBN,publisher,publicCiCon,paperdisPrice,edisPrice " +
                "from t_book b left join t_discount_price p on b.id = p.bookid where bookStatus=0 and (bookname LIKE ? or author LIKE ? or category Like ? or language Like ?) and price between ? and ? LIMIT ?,5","%"+keyWord+"%","%"+keyWord+"%","%"+keyWord+"%","%"+keyWord+"%",minPrice,maxPrice,(pageNo-1)*5);
    }

    @Override
    public List<Book> getBookListByLanguage(String language, Integer pageNo) {
        return executeQuery("select id,bookImg,bookName,price,eprice,author,saleCount,bookCount,bookStatus,description,category,format,`language`,dimensions,editStat,publicDate,ISBN,publisher,publicCiCon,paperdisPrice,edisPrice " +
                "from t_book b left join t_discount_price p on b.id = p.bookid where bookStatus=0 and language LIKE ? LIMIT ?,5",language,(pageNo-1)*5);
    }

    @Override
    public List<Book> getBookListHistory(Integer userId) {
        return executeQuery("select id,bookImg,bookName,author from t_book b left join t_history h on b.id = h.bookId where bookStatus=0 and h.userId = ? ORDER BY h.ViewTime DESC LIMIT 0,5",userId);
    }

    @Override
    public boolean updateHistory(Integer userId, Integer bookId) {
        if(bookId != null){
        return execute("DROP PROCEDURE IF EXISTS update_or_insert; -" +
                "create PROCEDURE update_or_insert() " +
                "begin " +
                "  IF EXISTS (select * from t_history where userId = "+userId+" and bookId = "+bookId+") THEN " +
                "    UPDATE t_history SET viewTime = NOW() WHERE userId = "+userId+" and bookId = "+bookId+"; " +
                "  ELSE " +
                "    insert INTO t_history (userId,bookId,viewTime) VALUES ("+userId+","+bookId+",NOW()); " +
                "  END IF; " +
                "end; -" +
                "call update_or_insert();");}
        else { return true;}
    }

    @Override
    public Book getBook(Integer id) {
        return load("select id,bookImg,bookName,price,eprice,author,saleCount,bookCount,bookStatus,description,category,format,`language`,dimensions,editStat,publicDate,ISBN,publisher,publicCiCon,paperdisPrice,edisPrice " +
                "from t_book b left join t_discount_price p on b.id = p.bookid where id = ? " , id);
    }

    @Override
    public Integer getBookCount(String keyWord) {
        if ("Bestselling".equals(keyWord)) {
            return ((Long) super.executeComplexQuery("select count(*) from t_book where bookStatus=0 and saleCount >= 0.25*bookCount")[0]).intValue();
        }else {
            return ((Long) super.executeComplexQuery("select count(*) from t_book where bookStatus=0 and (bookname LIKE ? or author LIKE ? or category Like ?)", "%" + keyWord + "%", "%" + keyWord + "%", "%" + keyWord + "%")[0]).intValue();
        }
    }

    @Override
    public Integer getBookCount(String keyWord, Double minPrice, Double maxPrice) {
        if ("Bestselling".equals(keyWord)) {
            return ((Long) super.executeComplexQuery("select count(*) from t_book where bookStatus=0 and saleCount >= 0.25*bookCount and price between ? and ?",minPrice,maxPrice)[0]).intValue();
        }else {
            return ((Long) super.executeComplexQuery("select count(*) from t_book where bookStatus=0 and (bookname LIKE ? or author LIKE ? or category Like ? and price between ? and ?)", "%" + keyWord + "%", "%" + keyWord + "%", "%" + keyWord + "%",minPrice,maxPrice)[0]).intValue();
        }
    }

    @Override
    public List<Book> getBestSellBook(int pageNo) {
        return executeQuery("select id,bookImg,bookName,price,eprice,author,saleCount,bookCount,bookStatus,description,category,format,`language`,dimensions,editStat,publicDate,ISBN,publisher,publicCiCon,paperdisPrice,edisPrice " +
                "from t_book b left join t_discount_price p on b.id = p.bookid where bookStatus=0 and saleCount >= 0.25*bookCount LIMIT ?,5",(pageNo-1)*5);
    }

    @Override
    public int addBuyCount(Integer id, String state, Integer buyCount) {
        return executeUpdate("update t_book SET saleCount = saleCount + ? where id = ?", buyCount, id);
    }

    @Override
    public int deleteBook(Integer id){
        return executeUpdate("delete from t_book where id=?",id);
    }

    @Override
    public int updateBook(Integer bookid, String colName, String value) {
            return executeUpdate("UPDATE t_book SET "+colName+" = ? WHERE id = ?",value,bookid);
    }

    @Override
    public int createBook(){
        executeUpdate("INSERT INTO t_book (id) VALUES (0)");
        Object[] resu = super.executeComplexQuery("SELECT MAX(id) id from t_book;");

        return (int)resu[0];
    }

}
