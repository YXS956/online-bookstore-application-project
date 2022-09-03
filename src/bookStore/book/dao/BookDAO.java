package bookStore.book.dao;

import bookStore.book.pojo.Book;

import java.util.List;

public interface BookDAO {
    List<Book> getBookList(Integer pageNo);

    Integer getTotalBookNo();

    Book getRandomBook(Integer index);

    List<Book> getAllBook();
    List<Book> getBookListByKey(String keyWord, Integer pageNo);
    List<Book> getBookListByPrice(Double minPrice, Double maxPrice, Integer pageNo);
    List<Book> getBookListByKeyPrice(String keyWord,Double minPrice, Double maxPrice, Integer pageNo);
    List<Book> getBookListByLanguage(String language, Integer pageNo);

    boolean updateHistory(Integer userId, Integer bookId);

    List<Book> getBookListHistory(Integer userId);

    Book getBook(Integer id);
    //查询总条数
    Integer getBookCount(String keyWord);

    Integer getBookCount(String keyWord, Double minPrice, Double maxPrice);

    List<Book> getBestSellBook(int pageNo);
    int addBuyCount(Integer id, String state, Integer buyCount);
    int deleteBook(Integer id);
    int updateBook(Integer bookid, String colName, String value);

    int createBook();
}
