import org.junit.Test;
import ru.vsu.cs.course1.task.Task;
import ru.vsu.cs.course1.task.TaskSecondary;
import ru.vsu.cs.course1.task.TaskUtils;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class SolutionTests {
	@Test
	public void test1() {
		String text = "Карова дает малако каторае пъут дэти";
		List<String> roots = TaskUtils.convertStringArrayToStringList(new String[]{
				"молок", "коров", "апельсин", "котор", "дети"
		});
		text = Task.processText(text, roots);
		String expected = "Корова дает малако которае пъут дети";
		assertEquals(expected, text);
	}
	@Test
	public void testToUpperCyrillic(){
		char c = 'б';
		c = TaskSecondary.toUpper(c);
		assertEquals('Б', c);
	}
	@Test
	public void testToUpperLatin(){
		char c = 'b';
		c = TaskSecondary.toUpper(c);
		assertEquals('B', c);
	}
	@Test
	public void testToLowerCyrillic(){
		char c = 'Б';
		c = TaskSecondary.toLower(c);
		assertEquals('б', c);
	}
	@Test
	public void testToLowerLatin(){
		char c = 'B';
		c = TaskSecondary.toLower(c);
		assertEquals('b', c);
	}
}
