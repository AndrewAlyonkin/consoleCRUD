package edu.alenkin.service;

import edu.alenkin.exception.ExistException;
import edu.alenkin.exception.NotExistException;
import edu.alenkin.model.Label;
import edu.alenkin.repository.LabelRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Alenkin Andrew
 * oxqq@ya.ru
 */

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class LabelServiceImplTest {
    @Mock
    private LabelRepository mockRepository;
    @InjectMocks
    LabelServiceImpl labelService = new LabelServiceImpl();
    private ArgumentCaptor<Label> labelCaptor;
    private ArgumentCaptor<Long> idCaptor;

    private Label testLabel;
    private Long postId;

    @BeforeAll
    public void init(){
        MockitoAnnotations.initMocks(this);
    }

    @BeforeEach
    void setUp() {
        testLabel = new Label("testName");
        postId = 10L;
        labelCaptor = ArgumentCaptor.forClass(Label.class);
        idCaptor = ArgumentCaptor.forClass(Long.class);
    }

    @Test
    void addLabelTest() throws SQLException, NotExistException, ExistException {
        labelService.addLabel(testLabel, postId);
        Mockito.verify(mockRepository).addLabel(labelCaptor.capture(), idCaptor.capture());
        assertEquals(testLabel, labelCaptor.getValue());
        assertEquals(postId, idCaptor.getValue());
    }

    @Test
    void removeLabelByIdTest() throws SQLException, NotExistException, ExistException {
        labelService.removeLabelById(postId);
        Mockito.verify(mockRepository).removeLabel(idCaptor.capture());
        assertEquals(postId, idCaptor.getValue());
    }
}