import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class TestDemoTest {

		
		private TestDemo testDemo;
		
	
	
	@BeforeEach
	void setUp() throws Exception {
		testDemo = new TestDemo();
		
	}

	@ParameterizedTest
	
	@MethodSource("TestDemoTest#argumentsForAddPositive")
	
	void assertThatTwoPositiveNumbersAreAddedCorrectly(int a, int b, int expected, Boolean expectException) {
		
		if(!expectException) {
			
			// Then: the zeros are removed properly
			  assertThat(testDemo.addPositive(a, b)).isEqualTo(expected);
			}
		
			else {
			assertThatThrownBy(() -> 
			testDemo.addPositive(a, b))
	        	.isInstanceOf(IllegalArgumentException.class);

			}
		
	}
		
	
	static Stream<Arguments> argumentsForAddPositive() {
		// When: the method is called to remove zeros
		return Stream.of(arguments(2, 4, 6, false), arguments(0, 0, 0, true), arguments(2, 0, 2, true), 
				arguments(-1, 2, 1, true));
		
	}

	

	@Test
	void assertThatNumberSquaredIsCorrect() {
		// Given: a numeric string to test NumberSquared
		TestDemo mockDemo = spy(testDemo);
		
		doReturn(5).when(mockDemo).getRandomInt();
		
		int fiveSquared = mockDemo.randomNumberSquared();
		
		assertThat(fiveSquared).isEqualTo(25);
}
	
	
	
	
}