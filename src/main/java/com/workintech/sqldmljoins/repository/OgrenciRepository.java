package com.workintech.sqldmljoins.repository;

import com.workintech.sqldmljoins.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OgrenciRepository extends JpaRepository<Ogrenci, Long> {


    //Kitap alan öğrencilerin öğrenci bilgilerini listeleyin..
    String QUESTION_2 = "SELECT * FROM ogrenci  WHERE ogrno In \n" +
            "(SELECT ogrno FROM islem )";
    @Query(value = QUESTION_2, nativeQuery = true)
    List<Ogrenci> findStudentsWithBook();


    //Kitap almayan öğrencileri listeleyin.
    String QUESTION_3 = "SELECT ogrenci.* FROM ogrenci\n" +
            "LEFT JOIN islem ON ogrenci.ogrno=islem.ogrno\n" +
            "WHERE islem.ogrno IS NULL ;";
    @Query(value = QUESTION_3, nativeQuery = true)
    List<Ogrenci> findStudentsWithNoBook();

    //10A veya 10B sınıfındaki öğrencileri sınıf ve okuduğu kitap sayısını getirin.
    String QUESTION_4 = "SELECT sinif,  COUNT(ogrno)  FROM ogrenci WHERE (sinif='10A' OR sinif='10B') AND  ogrno IN (SELECT ogrno FROM islem )\n" +
            "GROUP BY sinif;";
    @Query(value = QUESTION_4, nativeQuery = true)
    List<KitapCount> findClassesWithBookCount();

    //Öğrenci tablosundaki öğrenci sayısını gösterin
    String QUESTION_5 = "SELECT COUNT(ogrno) FROM ogrenci;";
    @Query(value = QUESTION_5, nativeQuery = true)
    Integer findStudentCount();

    //Öğrenci tablosunda kaç farklı isimde öğrenci olduğunu listeleyiniz.
    String QUESTION_6 = "SELECT  count(DISTINCT ad)  FROM  ogrenci;";
    @Query(value = QUESTION_6, nativeQuery = true)
    Integer findUniqueStudentNameCount();

    //--İsme göre öğrenci sayılarının adedini bulunuz.
    //--Ali: 2, Mehmet: 3
    String QUESTION_7 = "\n" +
            "SELECT ad,count(ad) FROM ogrenci GROUP BY ad;\n";
    @Query(value = QUESTION_7, nativeQuery = true)
    List<StudentNameCount> findStudentNameCount();


    String QUESTION_8 = "SELECT sinif, COUNT(sinif) FROM ogrenci GROUP BY sinif;";
    @Query(value = QUESTION_8, nativeQuery = true)
    List<StudentClassCount> findStudentClassCount();

    String QUESTION_9 = "SELECT o.ad , o.soyad, count(i.kitapno) as kSayisi FROM ogrenci as o INNER JOIN islem as i ON o.ogrno=i.ogrno \n" +
            "GROUP BY o.ad,o.soyad;";
    @Query(value = QUESTION_9, nativeQuery = true)
    List<StudentNameSurnameCount> findStudentNameSurnameCount();
}
