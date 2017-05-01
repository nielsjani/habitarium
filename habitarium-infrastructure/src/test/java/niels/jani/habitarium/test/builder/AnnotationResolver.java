package niels.jani.habitarium.test.builder;

import org.springframework.context.ApplicationContext;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

class AnnotationResolver {

    void resolveForBuilding(Object obj) {
        for (Field field : getEmptyFieldsWithGenerateDefaultValueAnnotation(obj)) {
            TestBuilder<?> builder = createTestBuilder(field);
            setValue(obj, field, builder.build());
        }
    }

	void resolveForPersisting(Object obj, ApplicationContext context) {
		for (Field field : getEmptyFieldsWithGenerateDefaultValueAnnotation(obj)) {
			TestBuilder<?> builder = createTestBuilder(field);
			setValue(obj, field, builder.persist(context));
		}
	}

    private List<Field> getEmptyFieldsWithGenerateDefaultValueAnnotation(Object obj) {
        return getFields(obj).stream()
            .filter(field -> field.isAnnotationPresent(CreateDefaultWithBuilder.class))
            .filter(field -> valueIsNull(field, obj))
            .collect(toList());
    }

    private List<Field> getFields(Object obj) {
        return getFields(obj.getClass());
    }

    private List<Field> getFields(Class<?> clazz) {
        List<Field> fields = new ArrayList<>();
        fields.addAll(asList(clazz.getDeclaredFields()));
        fields.addAll(getFieldsFromSuperClass(clazz));
        return fields;
    }

    private List<Field> getFieldsFromSuperClass(Class<?> clazz) {
        Class<?> parent = clazz.getSuperclass();
        if (parent != null) {
            return getFields(parent);
        }
        return emptyList();
    }

    private boolean valueIsNull(Field field, Object obj) {
        try {
            field.setAccessible(true);
            return field.get(obj) == null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

	private TestBuilder<?> createTestBuilder(Field field) {
		return createTestBuilder(field.getType());
	}

	private TestBuilder<?> createTestBuilder(Class<?> clazz) {
		try {
			String className = clazz.getName() + "TestBuilder";
			Class<?> builderClass = clazz.getClassLoader().loadClass(className);
			Constructor<?> constructor = builderClass.getDeclaredConstructor();
			constructor.setAccessible(true);
			return (TestBuilder<?>) constructor.newInstance();
		} catch (Exception e) {
			throw new RuntimeException("No test builder found for " + clazz.getCanonicalName(), e);
		}
	}

	private void setValue(Object obj, Field field, Object value) {
		try {
			field.set(obj, value);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
