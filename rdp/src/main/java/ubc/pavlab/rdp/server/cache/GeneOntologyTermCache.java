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

package ubc.pavlab.rdp.server.cache;

import java.util.Collection;

import ubc.pavlab.rdp.server.model.GeneOntologyTerm;

/**
 * TODO Document Me
 * 
 * @author mjacobson
 * @version $Id$
 */
public interface GeneOntologyTermCache {

    public boolean hasExpired();

    public Collection<GeneOntologyTerm> fetchByIds( Collection<String> ids );

    public GeneOntologyTerm fetchById( String id );

    public Collection<GeneOntologyTerm> fetchByQuery( String queryString );

    public void putAll( Collection<GeneOntologyTerm> terms );

    public void clearAll();

    public long size();

}