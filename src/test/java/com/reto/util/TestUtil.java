package com.reto.util;

import com.fasterxml.jackson.databind.ObjectMapper;
//import com.mysql.cj.util.TestUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.math3.stat.inference.TestUtils;
import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <b>Class</b>:TestUtil<br/>
 * <b>Company</b>:Coroda Automatic Door.<br/>
 *
 * @author Coroda Automatic Door<br/>
 * @version 1.0
 */
@Slf4j
public class TestUtil {

  private TestUtil() {
  }

  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  /**
   * Método para convertir un string en objeto.
   *
   * @param path path
   * @param classObject classObject
   * @param <T> generic Type
   * @return generic Class
   */
  @SneakyThrows
  public static <T> T fromFileFactory(String path, Class<T> classObject) {
    return OBJECT_MAPPER.readValue(new ClassPathResource(path).getInputStream(), classObject);
  }

  /**
   * Método para convertir un string en lista de objetos.
   *
   * @param path path
   * @param classObjectArray classObjectArray
   * @param <T> generic Type
   * @return generic Array
   * @throws IOException IOException
   */
  public static <T> List<T> getListFromFileFactory(String path,
                                                   Class<T[]> classObjectArray) throws IOException {
    return Arrays.asList(OBJECT_MAPPER
        .readValue(new ClassPathResource(path).getInputStream(), classObjectArray));
  }

  public static String readJsonFileToString(String fileName) {
    String result = null;
    try (BufferedReader br = new BufferedReader(new InputStreamReader(
        Objects.requireNonNull(TestUtils.class.getClassLoader().getResourceAsStream(fileName)),
        StandardCharsets.UTF_8))) {
      result = br.lines().collect(Collectors.joining());
    } catch (Exception ex) {
      log.info(ex.toString());
    }

    return result;
  }

}
