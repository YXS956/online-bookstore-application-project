package bookStore.book.service;

import bookStore.book.pojo.Book;

import java.util.List;

public interface BookService {
    List<Book> getBookList(Integer pageNo);

    Integer getTotalBookCount();

    List<Book> getRandomBookList(List<Integer> placeNumbers);

    List<Book> getAllBook();
    List<Book> getBookListByKey(String keyWord, Integer pageNo);

    List<Book> getBookListByKeyPrice(String keyWord, Double minPrice, Double maxPrice, Integer pageNo);

    List<Book> getBookListByLan(String language, Integer pageNo);

    List<Book> getBookListHistory(Integer userId);

    boolean updateHistory(Integer userId, Integer bookId);

    Book getBook(Integer id);
    Integer getBookCount(String keyWord);

    Integer getBookCount(String keyWord, Double minPrice, Double maxPrice);

    List<Book> getBestSellBookList(int i);

    List<Book> getBookListById(int bookId);
    int addBuyCount(Integer id, String state,Integer buyCount);
    int deleteBook(Integer id);
    int updateBookColumn(Integer bookid, String colName, String value);

    int createBook();
}
