package ubc.pavlab.rdp.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.util.collections.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Import;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ubc.pavlab.rdp.WebSecurityConfig;
import ubc.pavlab.rdp.model.GeneInfo;
import ubc.pavlab.rdp.model.Taxon;
import ubc.pavlab.rdp.model.User;
import ubc.pavlab.rdp.model.UserGene;
import ubc.pavlab.rdp.model.enums.TierType;
import ubc.pavlab.rdp.services.*;
import ubc.pavlab.rdp.settings.ApplicationSettings;
import ubc.pavlab.rdp.settings.SiteSettings;

import java.util.EnumSet;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ubc.pavlab.rdp.util.TestUtils.*;

@WebMvcTest(ApiController.class)
@RunWith(SpringRunner.class)
@Import(WebSecurityConfig.class)
public class ApiControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    MessageSource messageSource;
    @MockBean
    private UserService userService;
    @MockBean
    private TaxonService taxonService;
    @MockBean
    private GeneInfoService geneService;
    @MockBean
    TierService tierService;
    @MockBean
    private UserOrganService userOrganService;
    @MockBean
    UserGeneService userGeneService;
    @MockBean
    OrganInfoService organInfoService;
    @MockBean
    private ApplicationSettings applicationSettings;
    @MockBean
    private ApplicationSettings.InternationalSearchSettings isearchSettings;
    @MockBean
    private SiteSettings siteSettings;
    @MockBean
    private UserDetailsService userDetailsService;
    @MockBean
    private PermissionEvaluator permissionEvaluator;

    @Before
    public void setUp() {
        given( applicationSettings.getIsearch() ).willReturn( isearchSettings );
        given( isearchSettings.isEnabled() ).willReturn( true );
    }

    @Test
    public void searchGenes_withSingleTier_thenReturn200() throws Exception {
        Taxon humanTaxon = createTaxon( 9606 );
        User user = createUser( 1 );
        GeneInfo cdh1GeneInfo = createGene( 1, humanTaxon );
        UserGene cdh1UserGene = createUserGene( 1, cdh1GeneInfo, user, TierType.TIER1 );

        given( taxonService.findById( 9606 ) ).willReturn( humanTaxon );
        given( geneService.findBySymbolAndTaxon( "CDH1", humanTaxon ) ).willReturn( cdh1GeneInfo );
        given( userGeneService.handleGeneSearch( cdh1GeneInfo, EnumSet.of( TierType.TIER1 ), Optional.of( humanTaxon ), Optional.empty(), Optional.empty() ) )
                .willReturn( Sets.newSet( cdh1UserGene ) );

        mvc.perform( get( "/api/genes/search" )
                .param( "symbol", "CDH1" )
                .param( "taxonId", "9606" )
                .param( "tier", "TIER1" ) )
                .andExpect( status().is2xxSuccessful() );

        verify( userGeneService ).handleGeneSearch( cdh1GeneInfo, EnumSet.of( TierType.TIER1 ), Optional.empty(), Optional.empty(), Optional.empty() );
    }

    @Test
    public void searchGenes_withMultipleTiers_thenReturn200() throws Exception {
        Taxon humanTaxon = createTaxon( 9606 );
        User user = createUser( 1 );
        GeneInfo cdh1GeneInfo = createGene( 1, humanTaxon );
        UserGene cdh1UserGene = createUserGene( 1, cdh1GeneInfo, user, TierType.TIER1 );

        given( taxonService.findById( 9606 ) ).willReturn( humanTaxon );
        given( geneService.findBySymbolAndTaxon( "CDH1", humanTaxon ) ).willReturn( cdh1GeneInfo );
        given( userGeneService.handleGeneSearch( cdh1GeneInfo, EnumSet.of( TierType.TIER1, TierType.TIER2 ), Optional.of( humanTaxon ), Optional.empty (), Optional.empty() ) )
                .willReturn( Sets.newSet( cdh1UserGene ) );

        mvc.perform( get( "/api/genes/search" )
                .param( "symbol", "CDH1" )
                .param( "taxonId", "9606" )
                .param( "tiers", "TIER1" )
                .param( "tiers", "TIER2" ) )
                .andExpect( status().is2xxSuccessful() );

        verify( userGeneService ).handleGeneSearch( cdh1GeneInfo, EnumSet.of( TierType.TIER1, TierType.TIER2 ), Optional.empty(), Optional.empty(), Optional.empty() );
    }

    @Test
    public void searchGenes_withNoTiers_thenReturn200() throws Exception {
        Taxon humanTaxon = createTaxon( 9606 );
        User user = createUser( 1 );
        GeneInfo cdh1GeneInfo = createGene( 1, humanTaxon );
        UserGene cdh1UserGene = createUserGene( 1, cdh1GeneInfo, user, TierType.TIER1 );

        given( taxonService.findById( 9606 ) ).willReturn( humanTaxon );
        given( geneService.findBySymbolAndTaxon( "CDH1", humanTaxon ) ).willReturn( cdh1GeneInfo );
        given( userGeneService.handleGeneSearch( cdh1GeneInfo, EnumSet.of( TierType.TIER1, TierType.TIER2 ), Optional.of( humanTaxon ), Optional.empty (), Optional.empty() ) )
                .willReturn( Sets.newSet( cdh1UserGene ) );

        mvc.perform( get( "/api/genes/search" )
                .param( "symbol", "CDH1" )
                .param( "taxonId", "9606" ) )
                .andExpect( status().is2xxSuccessful() );

        verify( userGeneService ).handleGeneSearch( cdh1GeneInfo, TierType.MANUAL, Optional.empty(), Optional.empty(), Optional.empty() );
    }
}