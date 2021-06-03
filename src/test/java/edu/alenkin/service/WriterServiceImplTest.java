package edu.alenkin.service;

import edu.alenkin.exception.ExistException;
import edu.alenkin.exception.NotExistException;
import edu.alenkin.model.Writer;
import edu.alenkin.repository.WriterRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.*;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Alenkin Andrew
 * oxqq@ya.ru
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class WriterServiceImplTest {
    @Mock
    WriterRepository writerMock;
    @InjectMocks
    WriterServiceImpl writerService = new WriterServiceImpl();
    ArgumentCaptor<Writer> writerCaptor;
    ArgumentCaptor<Long> idCaptor;
    private Writer testWriter;
    private List<Writer> testWritersList;
    private long testWriterId;

    @BeforeAll
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeEach
    void setUp() {
        writerCaptor = ArgumentCaptor.forClass(Writer.class);
        idCaptor = ArgumentCaptor.forClass(Long.class);
        testWriterId = 100L;
        testWriter = new Writer(testWriterId, "testName", "testSoName");
        testWritersList = Arrays.asList(
                new Writer(1L, "test1", "testSoName1"),
                new Writer(2L, "test2", "testSoName2"),
                new Writer(3L, "test3", "testSoName3"));
    }

    @Test
    void addWriter() throws SQLException, ExistException {
        writerService.addWriter(testWriter);
        Mockito.verify(writerMock).addWriter(writerCaptor.capture());
        assertEquals(testWriter, writerCaptor.getValue());
    }

    @Test
    void addExistWriter() throws SQLException, ExistException {
        Mockito.doThrow(new ExistException()).when(writerMock).addWriter(testWriter);
        assertThrows(ExistException.class, () -> writerService.addWriter(testWriter));
        Mockito.verify(writerMock).addWriter(writerCaptor.capture());
    }

    @Test
    void getWriter() throws SQLException, NotExistException, ExistException {
        Mockito.when(writerMock.getWriterById(testWriterId)).thenReturn(testWriter);
        Writer currentWriter = writerService.getWriter(testWriterId);
        Mockito.verify(writerMock).getWriterById(idCaptor.capture());
        assertEquals(testWriter, currentWriter);
    }

    @Test
    void getNotExistWriter() throws SQLException, NotExistException, ExistException {
        Mockito.when(writerMock.getWriterById(testWriterId)).thenThrow(new NotExistException());
        assertThrows(NotExistException.class,() -> writerService.getWriter(testWriterId));
        Mockito.verify(writerMock).getWriterById(idCaptor.capture());
    }

    @Test
    void removeWriter() throws SQLException, NotExistException, ExistException {
        writerService.removeWriter(testWriter);
        Mockito.verify(writerMock).removeWriter(writerCaptor.capture());
        assertEquals(testWriter, writerCaptor.getValue());
    }

    @Test
    void removeNotExistWriter() throws SQLException, NotExistException, ExistException {
        Mockito.doThrow(new NotExistException()).when(writerMock).removeWriter(testWriter);
        assertThrows(NotExistException.class, () -> writerService.removeWriter(testWriter));
        Mockito.verify(writerMock).removeWriter(writerCaptor.capture());
    }

    @Test
    void removeWriterById() throws SQLException, NotExistException, ExistException {
        writerService.removeWriterById(testWriterId);
        Mockito.verify(writerMock).removeWriterById(idCaptor.capture());
        assertEquals(testWriterId, idCaptor.getValue());
    }

    @Test
    void removeNotExistWriterById() throws SQLException, NotExistException, ExistException {
        Mockito.doThrow(new NotExistException()).when(writerMock).removeWriterById(testWriterId);
        assertThrows(NotExistException.class, () -> writerService.removeWriterById(testWriterId));
        Mockito.verify(writerMock).removeWriterById(idCaptor.capture());
    }


    @Test
    void updateWriter() throws SQLException, NotExistException, ExistException {
        writerService.updateWriter(testWriter);
        Mockito.verify(writerMock).updateWriter(writerCaptor.capture());
        assertEquals(testWriter, writerCaptor.getValue());
    }

    @Test
    void clearWriters() {
        writerService.clearWriters();
        Mockito.verify(writerMock).clear();
    }

    @Test
    void getAllWriters() throws SQLException, NotExistException, ExistException {
        Mockito.when(writerMock.getAllWriters()).thenReturn(testWritersList);
        List<Writer> currentList = writerService.getAllWriters();
        Mockito.verify(writerMock).getAllWriters();
        assertEquals(testWritersList, currentList);
    }
}