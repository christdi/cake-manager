package com.waracle.cakemgr;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.waracle.cakemgr.model.CakeModel;
import com.waracle.cakemgr.repository.CakeRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import static com.waracle.cakemgr.model.builder.CakeModelBuilder.aCakeModel;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CakeDataPopulatorTest {

    @InjectMocks
    private CakeDataPopulator cakeDataPopulator;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private CakeRepository cakeRepository;

    @Before
    public void setUp() throws Exception {
        Set<CakeModel> cakes = new HashSet<>();
        cakes.add(aCakeModel().build());

        when(objectMapper.readValue(any(URL.class), any(TypeReference.class))).thenReturn(cakes);
    }

    @Test
    public void GIVEN_a_cake_data_populator_WHEN_populate_method_called_with_valid_url_THEN_verify_retrieves_data_and_saves() throws Exception {
        ReflectionTestUtils.setField(cakeDataPopulator, "cakeDataUrl", "http://www.cakesrus.com/cakes.json");

        cakeDataPopulator.populateFromExternalURL();

        verify(objectMapper).readValue(any(URL.class), any(TypeReference.class));
        verify(cakeRepository).saveAll(any(Set.class));
    }

    @Test
    public void GIVEN_a_cake_data_populator_WHEN_populate_method_called_with_invalid_url_THEN_verify_no_data_saved() throws Exception {
        ReflectionTestUtils.setField(cakeDataPopulator, "cakeDataUrl", "blarg");

        cakeDataPopulator.populateFromExternalURL();

        verify(cakeRepository, times(0)).saveAll(any(Set.class));
    }

    @Test
    public void GIVEN_a_cake_date_populator_WHEN_populate_method_called_with_null_url_THEN_verify_no_data_saved() throws Exception {
        ReflectionTestUtils.setField(cakeDataPopulator, "cakeDataUrl", null);

        cakeDataPopulator.populateFromExternalURL();

        verify(cakeRepository, times(0)).saveAll(any(Set.class));
    }
}