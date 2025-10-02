/*

Copyright 2025 Massimo Santini

This file is part of "Programmazione 2 @ UniMI" teaching material.

This is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This material is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this file.  If not, see <https://www.gnu.org/licenses/>.

*/

package it.unimi.di.prog2.temisvolti.boolvect;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class BoolVectTest {

  static List<Class<? extends BoolVect>> IMPLEMENTATIONS =
      List.of(ArrayBoolVect.class, LongBoolVect.class, SetBoolVect.class);

  record ImplPair(Class<? extends BoolVect> u, Class<? extends BoolVect> v) {
    public BoolVect newInstanceU() {
      try {
        return (BoolVect) u.getConstructor().newInstance();
      } catch (Exception e) {
        return null;
      }
    }

    public BoolVect newInstanceV() {
      try {
        return (BoolVect) v.getConstructor().newInstance();
      } catch (Exception e) {
        return null;
      }
    }
  }

  static Stream<ImplPair> ulist() {
    List<ImplPair> wrap = new ArrayList<>();
    for (Class<? extends BoolVect> u : IMPLEMENTATIONS) wrap.add(new ImplPair(u, null));
    return wrap.stream();
  }

  static Stream<ImplPair> uvpairs() {
    List<ImplPair> crossprod = new ArrayList<>();
    for (Class<? extends BoolVect> u : IMPLEMENTATIONS)
      for (Class<? extends BoolVect> v : IMPLEMENTATIONS) crossprod.add(new ImplPair(u, v));
    return crossprod.stream();
  }

  @ParameterizedTest
  @MethodSource("ulist")
  void testDaStringaV(ImplPair one) {
    BoolVect u = one.newInstanceU();
    u.daString("VVF");
    final String us = "VFFFVVVFFFVV";
    u.daString(us);
    assertEquals(u.toString(), us);
  }

  @ParameterizedTest
  @MethodSource("ulist")
  void testDaStringaF(ImplPair one) {
    BoolVect u = one.newInstanceU();
    u.daString("VVF");
    final String us = "FFFFVFFFVVVFFFVV", expected = "VFFFVVVFFFVV";
    u.daString(us);
    assertEquals(u.toString(), expected);
  }

  @ParameterizedTest
  @MethodSource("ulist")
  void testToString0(ImplPair one) {
    BoolVect u = one.newInstanceU();
    assertEquals(u.toString(), "F");
  }

  @ParameterizedTest
  @MethodSource("uvpairs")
  void testAnd(ImplPair pair) {
    BoolVect u = pair.newInstanceU();
    BoolVect v = pair.newInstanceV();
    u.daString("VVFF");
    v.daString("FVFV");
    u.and(v);
    assertEquals(u.toString(), "VFF");
  }

  @ParameterizedTest
  @MethodSource("uvpairs")
  void testAndDifferingDimension(ImplPair pair) {
    BoolVect u = pair.newInstanceU();
    BoolVect v = pair.newInstanceV();
    u.daString("VVFVVVFF");
    v.daString("VFVFV");
    u.and(v);
    assertEquals(u.toString(), "VFVFF");
  }

  @ParameterizedTest
  @MethodSource("uvpairs")
  void testOr(ImplPair pair) {
    BoolVect u = pair.newInstanceU();
    BoolVect v = pair.newInstanceV();
    u.daString("VVFF");
    v.daString("FVFV");
    u.or(v);
    assertEquals(u.toString(), "VVFV");
  }

  @ParameterizedTest
  @MethodSource("uvpairs")
  void testOrDifferingDimension(ImplPair pair) {
    BoolVect u = pair.newInstanceU();
    BoolVect v = pair.newInstanceV();
    u.daString("VVFVVVFF");
    v.daString("VFVFV");
    u.or(v);
    assertEquals(u.toString(), "VVFVVVFV");
  }

  @ParameterizedTest
  @MethodSource("uvpairs")
  void testXor(ImplPair pair) {
    BoolVect u = pair.newInstanceU();
    BoolVect v = pair.newInstanceV();
    u.daString("VVFF");
    v.daString("FVFV");
    u.xor(v);
    assertEquals(u.toString(), "VFFV");
  }

  @ParameterizedTest
  @MethodSource("uvpairs")
  void testXorDifferingDimension(ImplPair pair) {
    BoolVect u = pair.newInstanceU();
    BoolVect v = pair.newInstanceV();
    u.daString("VVFVVVFF");
    v.daString("VFVFV");
    u.xor(v);
    assertEquals(u.toString(), "VVFFVFFV");
  }
}
