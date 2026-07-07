package com.example

import android.content.Context
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onRoot
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.data.WaterDatabase
import com.example.data.WaterRepository
import com.example.ui.WaterViewModel
import com.example.ui.theme.MyApplicationTheme
import com.github.takahirom.roborazzi.RobolectricDeviceQualifiers
import com.github.takahirom.roborazzi.captureRoboImage
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode

@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(qualifiers = RobolectricDeviceQualifiers.Pixel8, sdk = [36])
class GreetingScreenshotTest {

  @get:Rule val composeTestRule = createComposeRule()

  private lateinit var database: WaterDatabase
  private lateinit var repository: WaterRepository
  private lateinit var viewModel: WaterViewModel

  @Before
  fun setUp() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    database = Room.inMemoryDatabaseBuilder(context, WaterDatabase::class.java)
      .allowMainThreadQueries()
      .build()
    repository = WaterRepository(database.waterDao())
    viewModel = WaterViewModel(repository)
  }

  @After
  fun tearDown() {
    database.close()
  }

  @Test
  fun hydration_screen_screenshot() {
    // Add some test water
    viewModel.addWater(250)

    composeTestRule.setContent {
      MyApplicationTheme {
        HydrationScreen(viewModel = viewModel)
      }
    }

    composeTestRule.onRoot().captureRoboImage(filePath = "src/test/screenshots/greeting.png")
  }
}
