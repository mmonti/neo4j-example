package com.dreamworks.example;

import com.dreamworks.example.config.Neo4jEmbeddedConfig;
import com.dreamworks.example.model.Task;
import com.dreamworks.example.model.TaskVersion;
import com.dreamworks.example.model.request.TaskPayload;
import com.dreamworks.example.model.request.TaskVersionPayload;
import com.dreamworks.example.service.TaskService;
import com.dreamworks.example.service.TaskVersionService;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;


@Slf4j
@ActiveProfiles("test")
@ContextConfiguration(classes = { Neo4jEmbeddedConfig.class })
@RunWith(SpringRunner.class)
@SpringBootTest
public class TaskServiceTests {

    @Autowired
    private TaskService taskService;

    @Autowired
    private TaskVersionService taskVersionService;

    @Autowired
    private SessionFactory sessionFactory;

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
        // = Clear the graph with every test
        final Session session = sessionFactory.openSession();
        session.purgeDatabase();
    }

    @Test
    public void testTxIssue() throws Exception {
        // = We create a task.
        Task task = taskService.create(new TaskPayload("task"));

        // = We create a task version and link-it to the Task object.
        TaskVersion version1 = taskService.create(task.getId(), null, new TaskVersionPayload());

        // = We create a new task linked to the previous task version (version1) and also linked to the task.
        TaskVersion version2 = taskService.create(task.getId(), version1.getId(), new TaskVersionPayload());

        // Asserts on created objects.

        // = Ensure parent is wired up directly
        Assert.assertEquals(version1, version2.getParent());

        // = Ensure there are no children versions
        Assert.assertEquals(1, version2.getParent().getChilds().size());
        Assert.assertEquals(0, version2.getChilds().size());

        // Retrieve what is in the store and Asserts again with the same checks.

        // = Get the task by ID.
        Task storedTask = taskService.get(task.getId());

        // = Ensure the the task has 2 versions
        Assert.assertTrue(storedTask.hasVersions());
        Assert.assertEquals(1, storedTask.getVersions().size());

        // = Lets get what is in the store.
        TaskVersion storedVersion2 = taskVersionService.get(version2.getId());

        // = Check that parent of version2 has a child.
        Assert.assertEquals(1, storedVersion2.getParent().getChilds().size());

        // = Ensure there are no children versions in version2
        Assert.assertEquals(0, storedVersion2.getChilds().size());
    }
}