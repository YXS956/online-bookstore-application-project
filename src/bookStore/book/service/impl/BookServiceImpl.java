package bookStore.book.service.impl;

import bookStore.book.dao.BookDAO;
import bookStore.book.pojo.Book;
import bookStore.book.service.BookService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BookServiceImpl implements BookService {

    private BookDAO bookDAO ;

    @Override
    public List<Book> getBookList(Integer pageNo) {
        return bookDAO.getBookList(pageNo);
    }

    @Override
    public Integer getTotalBookCount() {    return bookDAO.getTotalBookNo();  }


    @Override
    public List<Book> getRandomBookList(List<Integer> placeNumbers) {
        List<Book> randomBookList = new ArrayList<>();

        for (int index : placeNumbers) {
            Book randomBook = bookDAO.getRandomBook(index);
            randomBookList.add(randomBook);
        }

        return randomBookList;
    }

    @Override
    public List<Book> getAllBook() {return bookDAO.getAllBook();}

    @Override
    public List<Book> getBookListByKey(String keyWord, Integer pageNo) {
        return bookDAO.getBookListByKey(keyWord,pageNo);
    }

    @Override
    public List<Book> getBookListByKeyPrice(String keyWord, Double minPrice, Double maxPrice, Integer pageNo) {
        if(!Objects.equals(keyWord, "")){
        return bookDAO.getBookListByKeyPrice(keyWord,minPrice,maxPrice,pageNo);
        }
        else {
            return bookDAO.getBookListByPrice(minPrice,maxPrice,pageNo);
        }
    }

    @Override
    public List<Book> getBookListByLan(String language, Integer pageNo) {
        return bookDAO.getBookListByLanguage(language,pageNo);
    }

    @Override
    public List<Book> getBookListHistory(Integer userId) {    return bookDAO.getBookListHistory(userId);  }

    @Override
    public boolean updateHistory(Integer userId, Integer bookId) { return bookDAO.updateHistory(userId,bookId);}

    @Override
    public Book getBook(Integer id) {
        return bookDAO.getBook(id);
    }

    @Override
    public Integer getBookCount(String keyWord) {
        return bookDAO.getBookCount(keyWord);
    }

    @Override
    public Integer getBookCount(String keyWord, Double minPrice, Double maxPrice) {
        return bookDAO.getBookCount(keyWord, minPrice, maxPrice);
    }

    @Override
    public List<Book> getBestSellBookList(int pageNo) {return bookDAO.getBestSellBook(pageNo);
    }

    @Override
    public List<Book> getBookListById(int bookId) {
        List<Book> bookList = new ArrayList<>();
        bookList.add(bookDAO.getBook(bookId));
        return bookList;
    }

    @Override
    public int addBuyCount(Integer id, String state, Integer buyCount) {
        return bookDAO.addBuyCount(id,state,buyCount);
    }

    @Override
    public int deleteBook(Integer id){
        return bookDAO.deleteBook(id);
    }

    @Override
    public int updateBookColumn(Integer bookid, String colName, String value) { return bookDAO.updateBook(bookid,colName,value);}

    @Override
    public int createBook() {
        return bookDAO.createBook();
    }


}
