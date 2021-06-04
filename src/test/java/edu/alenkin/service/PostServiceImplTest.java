package edu.alenkin.service;

import edu.alenkin.exception.ExistException;
import edu.alenkin.exception.NotExistException;
import edu.alenkin.model.Post;
import edu.alenkin.model.PostStatus;
import edu.alenkin.model.Writer;
import edu.alenkin.repository.PostRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.*;

import java.sql.SQLException;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Alenkin Andrew
 * oxqq@ya.ru
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PostServiceImplTest {
    @Mock
    PostRepository postMock;
    @InjectMocks
    PostServiceImpl postService = new PostServiceImpl();
    private ArgumentCaptor<Long> idCaptor;
    private ArgumentCaptor<Post> postCaptor;
    private ArgumentCaptor<Writer> writerCaptor;
    private long testPostId;
    private long testWriterId;
    private Post testPost;
    private Writer testWriter;

    @BeforeAll
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeEach
    void setUp() {
        Mockito.reset(postMock);
        testPostId = 10L;
        testWriterId = 100L;
        testPost = new Post(testPostId, "testContent", LocalDateTime.now());
        testPost.setStatus(PostStatus.DELETED);

        testWriter = new Writer(testWriterId, "testName", "testSoName");
        testWriter.addPost(testPost);
        idCaptor = ArgumentCaptor.forClass(Long.class);
        postCaptor = ArgumentCaptor.forClass(Post.class);
        writerCaptor = ArgumentCaptor.forClass(Writer.class);
    }

    @Test
    void removePostTest() throws SQLException, NotExistException, ExistException {
        postService.removePost(testPostId);
        Mockito.verify(postMock).removePost(idCaptor.capture());
        assertEquals(testPostId, idCaptor.getValue());
    }

    @Test
    void addPostTest() throws SQLException, NotExistException, ExistException {
        postService.addPost(testPost, testPostId);
        Mockito.verify(postMock).addPost(postCaptor.capture(), idCaptor.capture());
        assertEquals(testPost, postCaptor.getValue());
        assertEquals(testPostId, idCaptor.getValue());
    }

    @Test
    void updatePostsForWriterTest() throws SQLException, NotExistException, ExistException {
        postService.updatePostsForWriter(testWriter);
        Mockito.verify(postMock).updatePostsForWriter(writerCaptor.capture());
        assertEquals(testWriter, writerCaptor.getValue());
    }
}