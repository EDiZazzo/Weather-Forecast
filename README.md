# Backend Technical Take-Home 

This document describes the assignment to be completed by candidates interviewing for a backend position at [Shape](https://shape.dk).

👀 **Check out [our profile](https://github.com/shape-interviews) to better understand our technical interview process and the criteria we use to evaluate our candidates.**

## 📝 Problem Statement

A mobile app queries a 3rd party API for weather data, but after the app becomes popular the rate limit of the API is exceeded and we need to limit the number of requests made to the 3rd party API.

It is decided to introduce an integration layer between the app and the 3rd party API so that the app will no longer call the 3rd party API directly, but instead call the integration layer API that will ensure the rate limit towards the 3rd party is not exceeded while still serving data to the apps.

## 🖊️ Assignment

Your task is to build said integration layer which should expose a REST API returning data as JSON.

You are free to define the response structure, but please bear in mind that it should be easy for the clients to consume.

The API should have the following two methods:

### `GET /weather/summary`

Returns a list of the user’s favourite cities where the temperature will be above a certain temperature the next day. It is not required to store the user’s favorites server side - the client will pass those as part of the request.

**Parameters**
* unit (celsius|fahrenheit)
* temperature (int)
* cities (city IDs separated by comma)

Example request: `/weather/summary?unit=celsius&temperature=24&cities=2618425,3621849,3133880`

### `GET /weather/cities/<city_id>`

A list of temperatures for the next 5 days in one specific city.

**Parameters**

* city_id

Example request: `/weather/cities/2618425`

## 📋 Requirements

- You should use OpenWeatherMap as the 3rd party weather API. Imagine you are only allowed to call the API 10,000 times per day, but we want to support a much larger number of users. Sign up for a free API key here: https://openweathermap.org/api
- Use the 5 Day / 3 Hour Forecast API to query based on city ID: https://openweathermap.org/forecast5#cityid5

## 💬 Things to consider

* Keep a sensible project structure that can be expanded when the project grows in the future. Apply design patterns to help with this.
* Find a good way to ensure we stay within the rate limit of the 3rd party API while still serving the users with meaningful data.
* If time permits, implement handling for different error scenarios and cases of invalid input.
* Feel free to replace this README with your own.

## 💻 Practicalities

- We are not expecting you to spend more than 1 day (or 8 hours) on this task, but this is not a strict limitation. Feel free to exceed that time, if you feel like it.
- Rest assured that we are equally happy to evaluate an incomplete solution. We will only have less material to review, in such instance.
- Once your task is ready, please push it to this repository.
- Be prepared to the possibility to showcase your solution during a technical interview. In that case, you would be asked to run the application and show your solution from the user perspective, as well as to present the codebase, highlighting the most compelling parts of the solution, before answering ad-hoc questions concerning your application.
