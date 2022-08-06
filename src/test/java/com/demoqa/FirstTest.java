package com.demoqa;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static com.codeborne.selenide.Selenide.*;

public class FirstTest {
    Faker faker = new Faker();
    File file = new File("src/test/resources/image-for-autotest.jpg");

    String firstName, lastName, email, genderValue, phoneNumber, birthday, year, month, day, firstSubject, secondSubject,
            firstHobby, secondHobby, filename, currentAddress, state, city;
    int randomNum;

    @BeforeEach
    void configure() {
        Configuration.baseUrl = "https://demoqa.com";
        Configuration.browserSize = "770x1024";

        firstName = faker.name().firstName();
        lastName = faker.name().lastName();
        email = faker.internet().emailAddress();
        genderValue = "Other";
        randomNum = faker.number().numberBetween(100000, 999999);
        phoneNumber = "7888" + randomNum;
        birthday = "03 September,1948";
        year = "1948";
        month = "8";
        day = "003";
        firstSubject = "Arts";
        secondSubject = "Social Studies";
        firstHobby = "Sports";
        secondHobby = "Reading";
        filename = "image-for-autotest.jpg";
        currentAddress = faker.address().fullAddress();
        state = "Haryana";
        city = "Panipat";
    }

    @Test
    void fillAutomationPracticeForm() {
        // open page
        open("/automation-practice-form");

        // fill in fields with first name, last name, email
        $("#firstName").setValue(firstName);
        $("#lastName").setValue(lastName);
        $("#userEmail").setValue(email);

        // select gender
        $("#genterWrapper [value='" + genderValue + "']").doubleClick();

        // fill in phone number field
        $("#userNumber").setValue(phoneNumber);

        // select date of birth
        $("#dateOfBirthInput").click();
        $(".react-datepicker__month-select").click();
        $("[value='" + month + "']").click();
        $(".react-datepicker__year-select").click();
        $("[value='" + year + "']").scrollTo();
        $("[value='" + year + "']").click();
        $(".react-datepicker__month .react-datepicker__day--" + day).click();

        // select subjects
        $("#subjectsInput").click();
        $("#subjectsInput").setValue(firstSubject);
        $(".subjects-auto-complete__menu").click();
        $("#subjectsInput").setValue(secondSubject);
        $(".subjects-auto-complete__menu").click();

        // select hobbies via checkboxes
        $x("//*[@type='checkbox']/following-sibling::*[contains(text(), '" + firstHobby + "')]").click();
        $x("//*[@type='checkbox']/following-sibling::*[contains(text(), '" + secondHobby + "')]").click();

        // upload file with picture
        $("#uploadPicture").uploadFile(file);

        // fill in address field
        $("#currentAddress").setValue(currentAddress);

        // close google ads tab
        executeJavaScript("$('#fixedban').hide()");

        // select state and city
        $("#state").scrollTo();
        $("#state").click();
        $x("//*[@id='state']//*[contains(text(), '" + state + "')]").click();
        $("#city").click();
        $x("//*[@id='city']//*[contains(text(), '" + city + "')]").click();

        // click submit button
        $("#submit").click();

        // check results in the table
        $(".modal-content").shouldBe(Condition.visible);
        $x(
                "//*[contains(text(), 'Student Name')]/following-sibling::*[contains(text(), '" + firstName + " " + lastName + "')]"
        ).shouldBe(Condition.visible);
        $x(
                "//*[contains(text(), 'Student Email')]/following-sibling::*[contains(text(), '" + email + "')]"
        ).shouldBe(Condition.visible);
        $x(
                "//*[contains(text(), 'Gender')]/following-sibling::*[contains(text(), '" + genderValue + "')]"
        ).shouldBe(Condition.visible);
        $x(
                "//*[contains(text(), 'Mobile')]/following-sibling::*[contains(text(), '" + phoneNumber + "')]"
        ).shouldBe(Condition.visible);
        $x(
                "//*[contains(text(), 'Date of Birth')]/following-sibling::*[contains(text(), '" + birthday + "')]"
        ).shouldBe(Condition.visible);
        $x(
                "//*[contains(text(), 'Subjects')]/following-sibling::*[contains(text(), '" + firstSubject + ", " + secondSubject + "')]"
        ).shouldBe(Condition.visible);
        $x(
                "//*[contains(text(), 'Hobbies')]/following-sibling::*[contains(text(), '" + firstHobby + ", " + secondHobby + "')]"
        ).shouldBe(Condition.visible);
        $x(
                "//*[contains(text(), 'Picture')]/following-sibling::*[contains(text(), '" + filename + "')]"
        ).shouldBe(Condition.visible);

        $x(
                "//*[contains(text(), 'Address')]/following-sibling::*[contains(text(), '" + currentAddress + "')]"
        ).shouldBe(Condition.visible);
        $x(
                "//*[contains(text(), 'State and City')]/following-sibling::*[contains(text(), '" + state + " " + city + "')]"
        ).shouldBe(Condition.visible);
    }
}
