package org.greenstand.android.TreeTracker.orgpicker

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.greenstand.android.TreeTracker.MainCoroutineRule
import org.greenstand.android.TreeTracker.models.organization.OrgRepo
import org.greenstand.android.TreeTracker.utils.FakeFileGenerator
import org.greenstand.android.TreeTracker.utils.getOrAwaitValueTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class OrgPickerViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private val orgRepo = mockk<OrgRepo>(relaxed = true)
    private lateinit var orgPickerViewModel: OrgPickerViewModel

    @Before
    fun setup(){
        orgPickerViewModel = OrgPickerViewModel(orgRepo)
    }


    @Test
    @Throws(Exception::class)
    fun `verify Org Repo gets correct Org Set`() = runBlockingTest{
        val orgList = FakeFileGenerator.fakeOrganizationList
        coEvery { orgRepo.getOrgs() } returns orgList

        coVerify { orgRepo.getOrgs() }
    }

    @Test
    @Throws(Exception::class)
    fun `set fake organization, returns success with valid Org`()= runBlocking {
        val currentOrg = FakeFileGenerator.fakeOrganizationList.first()
        //Given
        coEvery { orgRepo.currentOrg() } returns  currentOrg

        // When
        orgPickerViewModel.setOrg(FakeFileGenerator.fakeOrganizationList.first())

        //Assert LiveData has correct data
        val result = orgPickerViewModel.state.getOrAwaitValueTest().currentOrg
        Assert.assertEquals(result, FakeFileGenerator.fakeOrganizationList.first())
    }

}