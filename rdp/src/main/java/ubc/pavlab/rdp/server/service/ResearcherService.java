/*
 * The rdp project
 * 
 * Copyright (c) 2014 University of British Columbia
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package ubc.pavlab.rdp.server.service;

import java.util.Collection;

import org.springframework.security.access.annotation.Secured;

import ubc.pavlab.rdp.server.model.Gene;
import ubc.pavlab.rdp.server.model.Researcher;

/**
 * TODO Document Me
 * 
 * @author ptan
 * @version $Id$
 */
public interface ResearcherService {

    @Secured({ "GROUP_USER" })
    public Researcher create( final Researcher researcher );

    @Secured({ "GROUP_USER" })
    public void update( Researcher researcher );

    @Secured({ "GROUP_USER", "AFTER_ACL_READ" })
    public Researcher findByEmail( final String email );

    @Secured({ "GROUP_USER", "AFTER_ACL_READ" })
    public Researcher findByUserName( final String username );

    @Secured({ "GROUP_ADMIN" })
    public void delete( Researcher researcher );

    @Secured({ "GROUP_ADMIN" })
    public Collection<Researcher> loadAll();

    @Secured({ "GROUP_USER", "AFTER_ACL_READ" })
    public Researcher thaw( Researcher researcher );

    @Secured({ "GROUP_USER" })
    public void addGenes( Researcher researcher, Collection<Gene> genes );

    @Secured({ "GROUP_USER" })
    public void removeGenes( Researcher researcher, Collection<Gene> genes );
}
