package ru.job4j.dream.servlet;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import ru.job4j.dream.store.MemStore;
import ru.job4j.dream.store.PsqlStore;
import ru.job4j.dream.store.Store;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(PsqlStore.class)
public class PostServletTest {

    @Test
    public void whenAddPostThenStoreIt() throws IOException {
        Store validate = MemStore.instOf();
        PowerMockito.mockStatic(PsqlStore.class);
        when(PsqlStore.instOf()).thenReturn(validate);
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        when(req.getParameter("op")).thenReturn("edit");
        when(req.getParameter("id")).thenReturn("0");
        when(req.getParameter("name")).thenReturn("CEO");
        when(req.getParameter("description")).thenReturn("some description");
        new PostServlet().doPost(req, resp);
        var it = validate.findAllPosts().stream().iterator();
        assertThat(it.next().getName(), is("Junior Java Job"));
        assertThat(it.next().getName(), is("Middle Java Job"));
        assertThat(it.next().getName(), is("Senior Java Job"));
        assertThat(it.next().getName(), is("CEO"));
        assertFalse(it.hasNext());
    }
}